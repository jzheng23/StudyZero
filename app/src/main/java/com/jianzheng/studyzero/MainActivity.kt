package com.jianzheng.studyzero

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.jianzheng.studyzero.receiver.UnlockReceiver
import com.jianzheng.studyzero.service.ForegroundService
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
        registerReceiver(unlockReceiver, IntentFilter(Intent.ACTION_USER_PRESENT))

        //Check overlay permission
        checkOverlayPermission()
//        if (!Settings.canDrawOverlays(this)) {
//            //startService(Intent(this, OverlayService::class.java))
//            checkOverlayPermission()
//        }

        //Check notification permission
        checkNotificationPermission()


        val foregroundServiceIntent = Intent(this, ForegroundService::class.java)
        ContextCompat.startForegroundService(this, foregroundServiceIntent)

    }

    private fun checkNotificationPermission() {
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()){
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, this.packageName)
            this.startActivity(intent)
        }
    }

    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                .putExtra(Settings.EXTRA_APP_PACKAGE, this.packageName)
            this.startActivity(intent)
//            val intent = Intent(
//                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                Uri.parse("package:$packageName")
//            )
//            startActivityForResult(
//                intent,
//                CODE_DRAW_OVER_OTHER_APP_PERMISSION
//            )
        }
    }

//    companion object {
//        private const val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084
//    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(unlockReceiver)
    }
}

