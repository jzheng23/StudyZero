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
    //var isScheduled: Boolean = false

    fun delayService(delayTimeMillis: Long, intent: Intent, context: Context?){
        job = CoroutineScope(Dispatchers.Main).launch {
            delay(delayTimeMillis)
            context?.startService(intent)
        }
    }

    fun cancelService(){
        job?.cancel()
        Log.d("unlock","Job cancelled!")
    }
}