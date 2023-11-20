package com.jianzheng.studyzero


import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class UsageStats : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d( "AppUsage", "Usage Created")
        val appOpsManager = getSystemService(APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOpsManager.unsafeCheckOpNoThrow(
                "android:get_usage_stats",
                Process.myUid(), packageName
            )
        } else {
            appOpsManager.checkOpNoThrow(
                "android:get_usage_stats",
                Process.myUid(), packageName
            )
        }
        if(mode == AppOpsManager.MODE_ALLOWED){
            Intent( Settings.ACTION_USAGE_ACCESS_SETTINGS ).apply {
                startActivity( this )
            }
        }
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val currentTime = System.currentTimeMillis()
        val usageEvents: UsageEvents = usageStatsManager.queryEvents( currentTime - 10000, currentTime)
        val usageEvent = UsageEvents.Event()
        while ( usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(usageEvent)
            Log.d( "AppUsage", "${usageEvent.packageName} ${usageEvent.timeStamp}")
        }

    }
    fun call(){
        Log.d( "AppUsage", "Usage Called")
    }

    /*
    private fun setUsageStatsPermission(){
        if(!checkUsageStatsPermission()){
            Intent( Settings.ACTION_USAGE_ACCESS_SETTINGS ).apply {
                startActivity( this )
            }
        }
    }
    private fun checkUsageStatsPermission() : Boolean {
        val appOpsManager = getSystemService(APP_OPS_SERVICE) as AppOpsManager
        // `AppOpsManager.checkOpNoThrow` is deprecated from Android Q
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOpsManager.unsafeCheckOpNoThrow(
                "android:get_usage_stats",
                Process.myUid(), packageName
            )
        } else {
            appOpsManager.checkOpNoThrow(
                "android:get_usage_stats",
                Process.myUid(), packageName
            )
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }



    fun getUsageStatsHistory() {
        //setUsageStatsPermission()
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val currentTime = System.currentTimeMillis()
        val usageEvents: UsageEvents = usageStatsManager.queryEvents( currentTime, currentTime)
        val usageEvent = UsageEvents.Event()
        while ( usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(usageEvent)
            Log.d( "AppUsage", "${usageEvent.packageName} ${usageEvent.timeStamp}")
        }
    }
    */

}
