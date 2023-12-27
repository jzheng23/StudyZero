package com.jianzheng.studyzero

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.jianzheng.studyzero.data.EsmDatabase
import com.jianzheng.studyzero.data.Response
import com.jianzheng.studyzero.receiver.UnlockReceiver
import com.jianzheng.studyzero.service.MyForegroundService
import com.jianzheng.studyzero.tool.MyPermissionChecker
import com.jianzheng.studyzero.ui.SettingPage
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val unlockReceiver = UnlockReceiver()
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.d("Main", "OnCreate")

        setContent {
            StudyZeroTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    SettingPage()
                    //ShowOverlayAlt()
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

        //start room
        val db = Room.databaseBuilder(applicationContext, EsmDatabase::class.java,"response_database").build()
        val responseDao = db.responseDao()
        GlobalScope.launch(Dispatchers.IO) {
            responseDao.insert(Response(id = 1, delay = 0, answer1 = 9, answer2 = 9, startingTime = 0, submittingTime = 0))
        }

        createSharedPreference()
    }

    private fun createSharedPreference() {
        val sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)

        // Open the editor to make changes
        val editor = sharedPreferences.edit()

        // Put values
        editor.putString("example_key", "Hello, SharedPreferences!")
            .putBoolean("first_run",false)
            .putInt("some_int",0)
            .apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(unlockReceiver)
    }


}