package com.jianzheng.studyzero

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.jianzheng.studyzero.receiver.UnlockReceiver
import com.jianzheng.studyzero.ui.ShowEsm
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme

class MainActivity : ComponentActivity() {
    private val unlockReceiver = UnlockReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyZeroTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    ShowEsm(isPreview = false)
                }
            }
        }
        registerReceiver(unlockReceiver, IntentFilter(Intent.ACTION_USER_PRESENT))
        Log.d("UnlockReceiver","MainActivity Started")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(unlockReceiver)
    }
}
