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
//    private val delayList = listOf<Int>(5, 8, 12, 18, 27, 41, 62, 95, 144, 220, 335, 510, 776, 1182, 1800)
    private val delayList = mutableListOf(1, 3, 5, 8, 12)
//    private val displayTimeMillis = 10000L
    private val extraTrigger = 18
    private var isScheduled = false
    private var triggerIndex: Int = 0

    fun delayService(intent: Intent, context: Context?){
        if (triggerIndex < delayList.size) {
            isScheduled = true
            Log.d("unlock","Delay starts!")
            job = CoroutineScope(Dispatchers.Main).launch {
                delay(delayList[triggerIndex] * 1000L)
                context?.startService(intent)
//                CoroutineScope(Dispatchers.Main).launch {
//                    delay(displayTimeMillis)
//                    context?.stopService(intent)
//                }
                isScheduled = false
                Log.d("unlock","Service starts!")
            }
            triggerIndex++
            Log.d("unlock","unlocked $triggerIndex times")
        } else {
            Log.d("unlock","Out of index")
        }

    }

    fun cancelService(){
        if (isScheduled) {
            Log.d("unlock","unlocked $triggerIndex times")
            recycleTrigger()
            job?.cancel()
            Log.d("unlock","Job cancelled! Len of list is ${delayList.size}")
        } else {
            Log.d("unlock","Nothing to cancel")
        }
    }

    fun recycleTrigger(){
        delayList += delayList[triggerIndex-1]
        Log.d("unlock","Trigger ${delayList[triggerIndex-1]} added to the list.")
    }
}