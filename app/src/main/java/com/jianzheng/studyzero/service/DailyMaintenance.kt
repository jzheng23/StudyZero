package com.jianzheng.studyzero.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class DailyMaintenance : Service() {
    fun uploadData(){}

    fun resetTriggerList(){}

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}