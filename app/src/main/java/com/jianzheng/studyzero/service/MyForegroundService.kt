package com.jianzheng.studyzero.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.R.drawable.notification_tile_bg
import androidx.core.app.NotificationCompat
import androidx.room.Room
import com.jianzheng.studyzero.MainActivity
import com.jianzheng.studyzero.data.EsmDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyForegroundService : Service() {

    private lateinit var notificationManager: NotificationManager
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var countOfResponses: Flow<Int>


    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val db = Room.databaseBuilder(applicationContext, EsmDatabase::class.java,"response_database").build()
        val responseDao = db.responseDao()
        countOfResponses = responseDao.getCount()
    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()


        updateCount()

        return START_STICKY
    }

    private fun updateCount() {
        serviceScope.launch() {
            countOfResponses.collect {
                val notification = buildForegroundNotification(it)
                startForeground(notificationId, notification)
                Log.d("notification","count updated!")
            }
        }
    }

    // Inside MyForegroundService class
    private fun createNotificationChannel() {
        val channel = NotificationChannel(channelId, "Foreground Service Channel", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
    }

    private fun buildForegroundNotification(count: Int = 0): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Study Zero")
            .setContentText("$count prompts completed today")
            .setSmallIcon(notification_tile_bg) // Set an icon for the notification
            .setContentIntent(pendingIntent)
            .build()
    }

    companion object {
        const val notificationId = 1
        const val channelId = "timer_channel"
    }

    override fun onDestroy() {
        super.onDestroy()
        //serviceScope.cancel()  // Cancel the scope when the service is destroyed
    }

}
