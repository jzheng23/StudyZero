package com.jianzheng.studyzero.tool

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat

object MyPermissionManager {

    private var isUserStatAllowed = true
    fun checkNotificationPermission(context: Context): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    fun grantNotificationPermission(context: Context) {
        val intent = Intent()
        intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, context.applicationInfo.uid)
        context.startActivity(intent)
    }

    fun checkOverlayPermission(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

    fun grantOverlayPermission(context: Context) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun checkUserStatPermission(context: Context): Boolean {
        return isUserStatAllowed
    }

    fun grantUserStatPermission(context: Context) {

//        if (checkNotificationPermission(context)) {
//            appScope.launchTry(Dispatchers.IO) {
//                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                // android.content.ActivityNotFoundException
//                // https://bugly.qq.com/v2/crash-reporting/crashes/d0ce46b353/113010?pid=1
//                context.startActivity(intent)
//            }
//        } else {
//            ToastUtils.showShort("必须先开启[通知权限]")
//        }
    }
}

