package com.jianzheng.studyzero.tool

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.jianzheng.studyzero.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.UUID
import java.util.concurrent.TimeUnit

object MyDelayManager {
    private var jobRandom: Job? = null
    private var jobTwenty: Job? = null
    private var jobThirty: Job? = null

    //    private val delayList = listOf<Int>(5, 8, 12, 18, 27, 41, 62, 95, 144, 220, 335, 510, 776)
    private val triggerList = mutableListOf(1, 3, 5, 8, 12)
    private const val extraTriggerThirty = 30L //one minute for testing
    private const val extraTriggerTwenty = 20L
    private var triggerIndex: Int = 0
    private var isJobScheduled = false
//    private val scope = CoroutineScope(Dispatchers.Default)
    private lateinit var myWorkManager: WorkManager
    private lateinit var randomTriggerRequestID: UUID
    
    fun delayService(context: Context) {
        if (triggerIndex < triggerList.size) {
            isJobScheduled = true
            val triggerRequest = OneTimeWorkRequestBuilder<MyWorker>()
                    .setInitialDelay(triggerList[triggerIndex].toLong(), TimeUnit.SECONDS)
                    .setInputData(workDataOf("tag" to context.getString(R.string.random_met)))
                    .build()
            val twentyTriggerRequest = OneTimeWorkRequestBuilder<MyWorker>()
//        .setInitialDelay(extraTriggerTwenty, TimeUnit.MINUTES)
                .setInitialDelay(extraTriggerTwenty, TimeUnit.SECONDS)
                .setInputData(workDataOf("tag" to context.getString(R.string._20met)))
                .build()
            val thirtyTriggerRequest = OneTimeWorkRequestBuilder<MyWorker>()
//        .setInitialDelay(extraTriggerThirty, TimeUnit.MINUTES)
                .setInitialDelay(extraTriggerThirty, TimeUnit.SECONDS)
                .setInputData(workDataOf("tag" to context.getString(R.string._30met)))
                .build()
            randomTriggerRequestID = triggerRequest.id
//            Log.d("unlock", "Delay starts!")
//            val scope = CoroutineScope(Dispatchers.Default)
//            jobRandom = scope.launch {
//                WorkManager.getInstance(context).enqueue(triggerRequest)
////                delay(delayList[triggerIndex] * 1000L)
////                context.startService(intent)
//                isJobScheduled = false
//                Log.d("unlock","index is $triggerIndex")
//            }
//            //check if the extra trigger has been met?
//            jobThirty = scope.launch {
//                WorkManager.getInstance(context).enqueue(thirtyTriggerRequest)
//            }
            myWorkManager = WorkManager.getInstance(context)
            myWorkManager.enqueue(triggerRequest)
            myWorkManager.enqueue(twentyTriggerRequest)
            myWorkManager.enqueue(thirtyTriggerRequest)
            triggerIndex++
//            Log.d("unlock","unlocked $triggerIndex times")
        } else {
            Log.d("unlock", "Out of index")
        }

    }

    fun cancelService() {

        myWorkManager.cancelWorkById(randomTriggerRequestID)

//        scope.coroutineContext.cancelChildren()
//        if (isJobScheduled) {
//            recycleTrigger()
//            Log.d("unlock", "Job cancelled! Len of list is ${triggerList.size}")
//        } else {
//            Log.d("unlock", "Nothing to cancel")
//        }
    }

    fun recycleTrigger() {
        triggerList += triggerList[triggerIndex - 1]
        Log.d("unlock", "Trigger ${triggerList[triggerIndex - 1]} added to the list.")
    }
}