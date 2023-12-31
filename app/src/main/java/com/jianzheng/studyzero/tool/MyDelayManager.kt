package com.jianzheng.studyzero.tool

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

object MyDelayManager {
    private var job1: Job? = null
    private var job2: Job? = null

    //    private val delayList = listOf<Int>(5, 8, 12, 18, 27, 41, 62, 95, 144, 220, 335, 510, 776, 1182, 1800)
    private val delayList = mutableListOf(1, 3, 5, 8, 12)

    //    private val displayTimeMillis = 10000L
    private const val extraTrigger = 1L
    private var isTriggerScheduled = false
    private var triggerIndex: Int = 0

    private val scope = CoroutineScope(Dispatchers.Default)
    private val triggerRequestList: MutableList<OneTimeWorkRequest> = mutableListOf()
    private val extraTriggerRequest = OneTimeWorkRequestBuilder<MyWorker>()
        .setInitialDelay(extraTrigger, TimeUnit.MINUTES)
        .build()

    fun delayService(intent: Intent, context: Context) {
        if (triggerIndex < delayList.size) {
            isTriggerScheduled = true
            val triggerRequest = OneTimeWorkRequestBuilder<MyWorker>()
                    .setInitialDelay(delayList[triggerIndex].toLong(), TimeUnit.SECONDS)
                    .build()
            Log.d("unlock", "Delay starts!")
//            val scope = CoroutineScope(Dispatchers.Default)
            job1 = scope.launch {
                WorkManager.getInstance(context).enqueue(triggerRequest)
//                delay(delayList[triggerIndex] * 1000L)
//                context.startService(intent)
//                isTriggerScheduled = false
                Log.d("unlock","index is $triggerIndex")
            }
            job2 = scope.launch {
                WorkManager.getInstance(context).enqueue(extraTriggerRequest)
            }
            triggerIndex++
//            Log.d("unlock","unlocked $triggerIndex times")
        } else {
            Log.d("unlock", "Out of index")
        }

    }

    fun cancelService() {

//        scope.cancel()
//        Log.d("unlock","Job cancelled!")
        job1?.cancel()
        job2?.cancel()
//        if (isTriggerScheduled) {
//            Log.d("unlock", "unlocked $triggerIndex times")
//            recycleTrigger()
//            job1?.cancel()
//            Log.d("unlock", "Job cancelled! Len of list is ${delayList.size}")
//        } else {
//            Log.d("unlock", "Nothing to cancel")
//        }
    }

    fun recycleTrigger() {
        delayList += delayList[triggerIndex - 1]
        Log.d("unlock", "Trigger ${delayList[triggerIndex - 1]} added to the list.")
    }
}