package com.jianzheng.studyzero.tool

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jianzheng.studyzero.service.OverlayService

class MyWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    //    val inputData = params.inputData
    override fun doWork(): Result {
        Log.d("unlock", "do work!")
        val tag = inputData.getString("tag")
        val serviceIntent = Intent(applicationContext, OverlayService::class.java).apply{
            putExtra("tag", tag)
        }
        applicationContext.startService(serviceIntent)
        return Result.success()
    }
}