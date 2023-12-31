package com.jianzheng.studyzero.tool

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jianzheng.studyzero.service.OverlayService

class MyWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        Log.d("unlock","do work!")
        val serviceIntent = Intent(applicationContext, OverlayService::class.java)
        applicationContext.startService(serviceIntent)
        return Result.success()
    }
}