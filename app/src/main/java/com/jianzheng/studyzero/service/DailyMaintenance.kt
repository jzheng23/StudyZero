package com.jianzheng.studyzero.service

import android.app.Application
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.jianzheng.studyzero.tool.ResponseCounter

class DailyMaintenance : Service() {
    fun uploadData(){}

    fun resetTriggerList(){
        val responseCounter = ResponseCounter(this)
        responseCounter.resetCounter()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}