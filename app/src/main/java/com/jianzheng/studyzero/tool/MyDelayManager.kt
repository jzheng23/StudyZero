package com.jianzheng.studyzero.tool

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.jianzheng.studyzero.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.concurrent.TimeUnit

object MyDelayManager {
    //    private val delayList = listOf<Int>(5, 8, 12, 18, 27, 41, 62, 95, 144, 220, 335, 510, 776)
    private val triggerList = mutableListOf(1, 5, 12)
    private const val extraTriggerThirty = 30L //30 minutes for real 30 seconds for testing
    private const val extraTriggerTwenty = 20L
    private var triggerIndex: Int = 0
    private var isJobScheduled = false
    private lateinit var myWorkManager: WorkManager
    private lateinit var randomTriggerRequestID: UUID



    fun delayService(context: Context) {
        myWorkManager = WorkManager.getInstance(context)
        val responseCounter = ResponseCounter(context)
        if (responseCounter.checkRandomMet()) {
            val triggerRequest = OneTimeWorkRequestBuilder<MyWorker>()
                .setInitialDelay(triggerList[triggerIndex].toLong(), TimeUnit.SECONDS)
                .setInputData(workDataOf("tag" to context.getString(R.string.random_met)))
                .build()
            myWorkManager.enqueue(triggerRequest)
            randomTriggerRequestID = triggerRequest.id
            triggerIndex++
        }
        if (responseCounter.check20Met()) {
            val twentyTriggerRequest = OneTimeWorkRequestBuilder<MyWorker>()
//                .setInitialDelay(extraTriggerTwenty, TimeUnit.MINUTES)
                .setInitialDelay(extraTriggerTwenty, TimeUnit.SECONDS)
                .setInputData(workDataOf("tag" to context.getString(R.string._20met)))
                .build()
            myWorkManager.enqueue(twentyTriggerRequest)
        }
        if (responseCounter.check30Met()) {
            val thirtyTriggerRequest = OneTimeWorkRequestBuilder<MyWorker>()
//                .setInitialDelay(extraTriggerThirty, TimeUnit.MINUTES)
                .setInitialDelay(extraTriggerThirty, TimeUnit.SECONDS)
                .setInputData(workDataOf("tag" to context.getString(R.string._30met)))
                .build()
            myWorkManager.enqueue(thirtyTriggerRequest)
        }

    }

    fun cancelService() {
        if (::myWorkManager.isInitialized and ::randomTriggerRequestID.isInitialized) {
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                if (myWorkManager.getWorkInfoById(randomTriggerRequestID).get().state
                    != WorkInfo.State.SUCCEEDED
                ) {
                    myWorkManager.cancelWorkById(randomTriggerRequestID)
                    recycleTrigger()
                }
            }
        }
    }

    fun recycleTrigger() {
        if (triggerIndex > 0) {
            triggerList += triggerList[triggerIndex - 1]
            Log.d("unlock", "Trigger ${triggerList[triggerIndex - 1]} added to the list.")
        }
    }
}