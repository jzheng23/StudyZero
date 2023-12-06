package com.jianzheng.studyzero.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.R.drawable.notification_tile_bg
import androidx.core.app.NotificationCompat
import com.jianzheng.studyzero.MainActivity

class MyForegroundService : Service() {

    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notification = buildForegroundNotification()
        startForeground(notificationId, notification)
        return START_STICKY
    }

    // Inside MyForegroundService class
    private fun createNotificationChannel() {
        val channel = NotificationChannel(channelId, "Foreground Service Channel", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
    }

    private fun buildForegroundNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Study Zero")
            .setContentText("x/15 prompts completed today")
            .setSmallIcon(notification_tile_bg) // Set an icon for the notification
            .setContentIntent(pendingIntent)
            .build()
    }

    companion object {
        const val notificationId = 1
        const val channelId = "timer_channel"
    }


}
