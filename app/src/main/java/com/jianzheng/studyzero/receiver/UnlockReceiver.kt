package com.jianzheng.studyzero.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import com.jianzheng.studyzero.service.OverlayService

class UnlockReceiver : BroadcastReceiver() {
    var startTimeMillis: Long = 0
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_USER_PRESENT) {
            startTimeMillis = SystemClock.elapsedRealtime()
            Log.d("Time", "Broadcast received at $startTimeMillis")
            val serviceIntent = Intent(context, OverlayService::class.java)
            val handler = Handler()
            handler.postDelayed({
                context?.startService(serviceIntent)
            }, 2000L) // 2 seconds delay
        }
    }

}