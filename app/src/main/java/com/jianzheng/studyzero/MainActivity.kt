package com.jianzheng.studyzero

import android.content.Context
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
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_USER_PRESENT)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        registerReceiver(unlockReceiver, filter)
        //start foreground service
        val myForegroundServiceIntent = Intent(this, MyForegroundService::class.java)
        ContextCompat.startForegroundService(this, myForegroundServiceIntent)

        val sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        if (!sharedPreferences.contains("index")) {
            val editor = sharedPreferences.edit()
            editor.putInt("index", 0)
            editor.apply()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(unlockReceiver)
    }


}