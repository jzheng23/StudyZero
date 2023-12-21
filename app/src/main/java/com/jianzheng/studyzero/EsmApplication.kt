package com.jianzheng.studyzero

import android.app.Application
import com.jianzheng.studyzero.data.AppContainer
import com.jianzheng.studyzero.data.AppDataContainer

class EsmApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}