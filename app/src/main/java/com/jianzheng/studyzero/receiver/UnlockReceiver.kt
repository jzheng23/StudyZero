package com.jianzheng.studyzero.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.jianzheng.studyzero.service.OverlayService
import com.jianzheng.studyzero.tool.MyDelayManager

class UnlockReceiver : BroadcastReceiver() {
    private var startTimeMillis: Long = 0
    private var unlockCounter: Int = 0
//    private val maxOfPrompts = 30 //TODO need to be the dynamic len of trigger list
    override fun onReceive(context: Context?, intent: Intent?) {
//        checkCountOfUnlock(context)
//        if (unlockCounter < maxOfPrompts) {
//
//        }
        when (intent?.action) {
            Intent.ACTION_USER_PRESENT -> {
//                startTimeMillis = SystemClock.elapsedRealtime()
                if (context != null) {
                    MyDelayManager.delayService(context)
                }
//                unlockCounter++
            }

            Intent.ACTION_SCREEN_OFF -> {
                val serviceIntent = Intent(context, OverlayService::class.java).apply{
                    putExtra("tag", "none")
                }
                context?.stopService(serviceIntent)
                MyDelayManager.cancelScheduledService()
            }
        }
    }

    private fun checkCountOfUnlock(context: Context?) {
        val sharedPreferences = context?.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)

        // Retrieve the string value, providing a default value if the key doesn't exist
        val retrievedValue = sharedPreferences?.getString("example_key", "Default Value")

        // Use the retrieved value as needed
        Log.d("preference","$retrievedValue")
    }
}
