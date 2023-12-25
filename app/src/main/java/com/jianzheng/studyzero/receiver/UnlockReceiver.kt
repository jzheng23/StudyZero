package com.jianzheng.studyzero.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import com.jianzheng.studyzero.MainActivity
import com.jianzheng.studyzero.service.OverlayService
import com.jianzheng.studyzero.tool.MyDelayManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UnlockReceiver : BroadcastReceiver() {
    private var startTimeMillis: Long = 0
    private val delayTimeMillis: Long = 1500
    private var unlockCounter: Int = 0
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_USER_PRESENT -> {
                unlockCounter++
                //Log.d("unlock","unlocked $unlockCounter times")
                val serviceIntent = Intent(context, OverlayService::class.java)
                startTimeMillis = SystemClock.elapsedRealtime()
                serviceIntent.putExtra("unlock", startTimeMillis)
                context?.stopService(serviceIntent)
                MyDelayManager.delayService(delayTimeMillis, serviceIntent, context)
//                val intent = Intent(context, MainActivity::class.java)
//                val job: Job? = CoroutineScope(Dispatchers.Main).launch {
//                    delay(delayTimeMillis)
//                    context?.startActivity(intent)
//                }

            }
            Intent.ACTION_SCREEN_OFF -> {
                //Log.d("unlock","Screen off!")
                MyDelayManager.cancelService()
            }
        }
    }
}
