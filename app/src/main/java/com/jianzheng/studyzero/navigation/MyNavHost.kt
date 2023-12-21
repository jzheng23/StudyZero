package com.jianzheng.studyzero.navigation

import android.content.Context
import android.util.Log
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jianzheng.studyzero.ui.EsmDestination
import com.jianzheng.studyzero.ui.EsmScreen
import com.jianzheng.studyzero.ui.SettingDestination
import com.jianzheng.studyzero.ui.SettingScreen

@Composable
fun MyNavHost(
    navController: NavHostController,
    windowManager: WindowManager,
    context: Context,
    isFromUnlock: Boolean,
    modifier: Modifier = Modifier,
){

    NavHost(
        navController = navController,
        startDestination = if (isFromUnlock) EsmDestination.route else SettingDestination.route,
        modifier = modifier,
    ){
        composable(route = SettingDestination.route) {
            SettingScreen(navigateToEsm = {
                navController.navigate(EsmDestination.route)
                Log.d("unlock","navigate to esm called")
            })

        }
        composable(route = EsmDestination.route) {
            EsmScreen(
//                navigateBack = {navController.navigate(SettingDestination.route)},
                navigateBack = {
                    if (isFromUnlock) {
                        navController.popBackStack()
                    } else navController.navigate(SettingDestination.route)
                               },
                windowManager = windowManager,
                context = context)
        }
    }
}