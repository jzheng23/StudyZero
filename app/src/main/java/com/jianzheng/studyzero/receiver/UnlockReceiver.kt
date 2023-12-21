package com.jianzheng.studyzero.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import com.jianzheng.studyzero.service.OverlayService
import com.jianzheng.studyzero.tool.MyDelayManager

class UnlockReceiver : BroadcastReceiver() {
    private var startTimeMillis: Long = 0
    private val delayTimeMillis: Long = 3000
    private var unlockCounter: Int = 0
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
//            Intent.ACTION_USER_PRESENT -> {
//                unlockCounter++
//                //Log.d("unlock","unlocked $unlockCounter times")
//                val serviceIntent = Intent(context, OverlayService::class.java)
//                startTimeMillis = SystemClock.elapsedRealtime()
//                serviceIntent.putExtra("unlock", startTimeMillis)
//                context?.stopService(serviceIntent)
//                MyDelayManager.delayService(delayTimeMillis, serviceIntent, context)
//            }
//            Intent.ACTION_SCREEN_OFF -> {
//                //Log.d("unlock","Screen off!")
//                MyDelayManager.cancelService()
//            }
        }
    }
}
