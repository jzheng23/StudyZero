package com.jianzheng.studyzero.receiver

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.compose.ui.platform.LocalContext
import com.jianzheng.studyzero.service.OverlayService

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
                if (!isMyServiceRunning(OverlayService::class.java, context)) {
                    context?.startService(serviceIntent)
                }
            }, delayTimeMillis) // 2 seconds delay
        }
    }
}

fun isMyServiceRunning(serviceClass: Class<*>, context: Context?): Boolean {
    val manager = context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}