package com.jianzheng.studyzero.ui

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jianzheng.studyzero.R
import com.jianzheng.studyzero.service.OverlayService
import com.jianzheng.studyzero.tool.MyDelayManager
import com.jianzheng.studyzero.tool.ResponseCounter

@Composable
fun TestingPage(
    userId: MutableState<String?>,
    modifier: Modifier,
    showDialog: MutableState<Boolean>
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.elevation))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .padding(mediumPadding)
        ) {
            Text(
                text = "Only for testing",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Text("User ID is ${userId.value}, upload is ${validateUid(userId.value)}")
            AuthCard(
                title = "ESM",
                desc = "Test the ESM page",
                onAuthClick = {
                    testFunction(context)
                },
                buttonText = "Test"
            )
            Divider()
            AuthCard(
                title = "Triggers",
                desc = "Reset triggers",
                onAuthClick = {
                    resetTriggerFunction(context)
                },
                buttonText = "Reset"
            )
            Divider()
            AuthCard(
                title = "User ID",
                desc = "Change User ID",
                onAuthClick = {
                    showDialog.value = true
                },
                buttonText = "Change"
            )
        }
    }
}

fun resetTriggerFunction(context: Context) {
    val responseCounter = ResponseCounter(context)
    responseCounter.resetCounter()
    MyDelayManager.resetTriggerList()
    Handler(Looper.getMainLooper()).post {
        val toast = Toast.makeText(context, "Triggers reset!", Toast.LENGTH_SHORT)
        toast.show()
    }
}

fun testFunction(context: Context) {
    val serviceIntent = Intent(context, OverlayService::class.java).apply {
        putExtra("tag", "testMet")
    }
    context.startService(serviceIntent)
}
