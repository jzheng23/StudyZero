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
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.jianzheng.studyzero.ui.ShowEsm
import com.jianzheng.studyzero.ui.UsagePage
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyZeroTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    ShowEsm(isPreview = false)
//                    UsagePage()
                }
            }
        }
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
        if(mode != AppOpsManager.MODE_ALLOWED){
            Intent( Settings.ACTION_USAGE_ACCESS_SETTINGS ).apply {
                startActivity( this )
            }
        }
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val currentTime = System.currentTimeMillis()
        val usageEvents = usageStatsManager.queryEvents(currentTime - 60000, currentTime)
        val usageEvent = UsageEvents.Event()
        while ( usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(usageEvent)
            Log.d( "AppUsage", "${usageEvent.packageName} ${usageEvent.timeStamp}")
        }
        Log.d( "AppUsage", "$mode")
        Log.d( "AppUsage", "$usageEvents")
    }
}
