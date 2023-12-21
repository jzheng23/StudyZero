package com.jianzheng.studyzero

import android.annotation.SuppressLint
import android.content.Context
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jianzheng.studyzero.navigation.MyNavHost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp(
    navController: NavHostController = rememberNavController(),
    windowManager: WindowManager,
    context: Context,
    isFromUnlock: Boolean,
) {
    MyNavHost(
        navController = navController,
        windowManager = windowManager,
        context = context,
        isFromUnlock = isFromUnlock
    )
}