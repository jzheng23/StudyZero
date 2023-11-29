package com.jianzheng.studyzero.service


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import androidx.core.R.drawable.notification_tile_bg
import androidx.core.app.NotificationCompat
import com.jianzheng.studyzero.MainActivity

class TimerService : Service() {

    //private var startTimeMillis: Long = 0
    private lateinit var notificationManager: NotificationManager
    private val notificationId = 1 // Unique ID for the notification

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.areNotificationsEnabled()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Save the start time when the service is started
        //startTimeMillis = SystemClock.elapsedRealtime()

        // Use a Handler to send the notification after 5 seconds
        val handler = Handler()
        handler.postDelayed({
            sendNotification()
            stopSelf() // Stop the service after sending the notification
        }, 3000L) // 3 seconds delay

        // Return START_NOT_STICKY to indicate that if the system kills the service,
        // it doesn't need to be restarted
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    // Create a notification channel for Android Oreo and above
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "timer_channel",
                "Timer Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Create the notification
    private fun sendNotification() {
        createNotificationChannel()

        val i = packageManager
            .getLaunchIntentForPackage(packageName)
            ?.setPackage(null)
            ?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)

        val pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_IMMUTABLE)
/*
        return NotificationCompat.Builder(context)
            // ... (other notification settings)
            .setContentIntent(pendingIntent)
            .build()


        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

 */

        val notification = NotificationCompat.Builder(this, "timer_channel")
            .setContentTitle("Hi")
            .setContentText("What's up?")
            .setSmallIcon(notification_tile_bg) // Replace with your icon
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setFullScreenIntent(pendingIntent, true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}