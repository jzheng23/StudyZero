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
    private var isScheduled = false
    private var unlockCounter: Int = 0

    fun delayService(intent: Intent, context: Context?){
        isScheduled = true
        Log.d("unlock","Delay starts!")
        job = CoroutineScope(Dispatchers.Main).launch {
            delay(delayList[unlockCounter] * 1000L)
            context?.startService(intent)
            isScheduled = false
            Log.d("unlock","Service starts!")
        }
        unlockCounter++
        Log.d("unlock","unlocked $unlockCounter times")
    }
//TODO what if dismissed by the user?
    fun cancelService(){
        if (isScheduled) {
//TODO add the current trigger to the end of the list
            Log.d("unlock","unlocked $unlockCounter times")
            delayList += delayList[unlockCounter-1]
            job?.cancel()
            Log.d("unlock","Job cancelled! Len of list is ${delayList.size}")
        } else {
            Log.d("unlock","Nothing to cancel")
        }
    }
}