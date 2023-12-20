package com.jianzheng.studyzero.tool

import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object MyDelayManager {
    private var job: Job? = null
    private var numberOfJob: Int = 0
    //var isScheduled: Boolean = false

    fun delayService(delayTimeMillis: Long, intent: Intent, context: Context?){
        //job?.cancel()
        job = CoroutineScope(Dispatchers.Main).launch {
            //isScheduled = true
            numberOfJob++
            Log.d("unlock","$numberOfJob scheduled jobs")
            delay(delayTimeMillis)
            context?.startService(intent)
            numberOfJob--
            Log.d("unlock","$numberOfJob scheduled jobs")
        }
    }
}