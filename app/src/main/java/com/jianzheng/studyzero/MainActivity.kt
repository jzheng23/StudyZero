package com.jianzheng.studyzero

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.core.content.ContextCompat
import com.jianzheng.studyzero.receiver.UnlockReceiver
import com.jianzheng.studyzero.service.MyForegroundService
import com.jianzheng.studyzero.tool.MyPermissionChecker
import com.jianzheng.studyzero.ui.SettingPage
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme

class MainActivity : ComponentActivity() {
    private val unlockReceiver = UnlockReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.d("Main", "OnCreate")
        setContent {
            StudyZeroTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    SettingPage()
                }
            }
        }
        //Check permissions
        MyPermissionChecker.checkOverlayPermission(this)
        MyPermissionChecker.checkNotificationPermission(this)

        //register receiver
        registerReceiver(unlockReceiver, IntentFilter(Intent.ACTION_USER_PRESENT))
        //start foreground service
        val myForegroundServiceIntent = Intent(this, MyForegroundService::class.java)
        ContextCompat.startForegroundService(this, myForegroundServiceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(unlockReceiver)
    }


}