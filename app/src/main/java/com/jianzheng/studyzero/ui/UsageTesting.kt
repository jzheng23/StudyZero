package com.jianzheng.studyzero.ui

import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.jianzheng.studyzero.UsageStats

@Composable
fun UsagePage() {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(context, UsageStats::class.java)
            context.startActivity(intent)
        }) {
        Text("Start usage")
    }
}

@Composable
fun CallUsagePage() {
    Button(
        onClick = {
        }) {
        Text("Print usage")
    }
}