package com.jianzheng.studyzero.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import com.jianzheng.studyzero.service.TimerService

class UnlockReceiver : BroadcastReceiver() {
    var startTimeMillis: Long = 0
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_USER_PRESENT) {
            //Log.d("UnlockReceiver","Unlocked")
            startTimeMillis = SystemClock.elapsedRealtime()
            Log.d("Time","Broadcast received at $startTimeMillis")
            val serviceIntent = Intent(context, TimerService::class.java)
            context?.startService(serviceIntent)
        }
    }

}