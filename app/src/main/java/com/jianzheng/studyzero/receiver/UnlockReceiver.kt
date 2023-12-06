package com.jianzheng.studyzero.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import com.jianzheng.studyzero.MainActivity
import com.jianzheng.studyzero.service.OverlayService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UnlockReceiver : BroadcastReceiver() {
    private var startTimeMillis: Long = 0
    private var delayTimeMillis: Long = 2000L
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_USER_PRESENT) {
            val serviceIntent = Intent(context, OverlayService::class.java)
            startTimeMillis = SystemClock.elapsedRealtime()
            serviceIntent.putExtra("unlock", startTimeMillis)
            val handler = Handler(Looper.myLooper()!!)
            handler.postDelayed({
                context?.startService(serviceIntent)
            }, delayTimeMillis) // 2 seconds delay
        }
    }
}