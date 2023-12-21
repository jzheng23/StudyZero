package com.jianzheng.studyzero

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.core.content.ContextCompat
import com.jianzheng.studyzero.receiver.UnlockReceiver
import com.jianzheng.studyzero.service.MyForegroundService
import com.jianzheng.studyzero.tool.MyPermissionChecker
import com.jianzheng.studyzero.ui.SettingScreen
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme

class MainActivity : ComponentActivity() {
    private val unlockReceiver = UnlockReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val destination = intent.getStringExtra("NAVIGATE_TO")
        //Log.d("unlock","calling from $destination")
        val isFromUnlock = destination == "esm"
        setContent {
            StudyZeroTheme {
                Surface {
                    MyApp(
                        windowManager = windowManager,
                        context = this,
                        isFromUnlock = isFromUnlock
                    )
                }
            }
        }
        //Check permissions
        MyPermissionChecker.checkOverlayPermission(this)
        MyPermissionChecker.checkNotificationPermission(this)

        //register receiver
        if (!isFromUnlock) {
            val filter = IntentFilter().apply {
                addAction(Intent.ACTION_USER_PRESENT)
                addAction(Intent.ACTION_SCREEN_OFF)
            }
            registerReceiver(unlockReceiver, filter)
            //start foreground service
            val myForegroundServiceIntent = Intent(this, MyForegroundService::class.java)
            ContextCompat.startForegroundService(this, myForegroundServiceIntent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        //unregisterReceiver(unlockReceiver)
    }


}