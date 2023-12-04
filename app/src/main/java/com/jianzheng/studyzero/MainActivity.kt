package com.jianzheng.studyzero

import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.jianzheng.studyzero.receiver.UnlockReceiver
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme

class MainActivity : ComponentActivity() {
    private val unlockReceiver = UnlockReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Main", "OnCreate")
        setContent {
            StudyZeroTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    Text("Lock and then unlock your phone to see the floating window")
                }
            }
        }
        registerReceiver(unlockReceiver, IntentFilter(Intent.ACTION_USER_PRESENT))
        Log.d("Time", "MainActivity Started")
        if (!Settings.canDrawOverlays(this)) {
            //startService(Intent(this, OverlayService::class.java))
            checkOverlayPermission()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(unlockReceiver)
    }

    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(
                intent,
                CODE_DRAW_OVER_OTHER_APP_PERMISSION
            )
        }
    }

    companion object {
        private const val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084
    }
}

