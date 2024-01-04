package com.jianzheng.studyzero.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Preview
@Composable
fun LoginPage(
    showDialog: MutableState<Boolean> = mutableStateOf(true)
) {
    if (showDialog.value) {
        Dialog(
            onDismissRequest = {
                showDialog.value = false
            }
        ){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UseIdInputField(showDialog)
                }
            }

        }
    }

}

fun saveUserId(userId: String, context: Context) {
    val userIdIsValidate: Boolean = validateUid(userId)
    val sharedPreferences =
        context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("UID", userId)
        .putBoolean("UID_valid", userIdIsValidate)
        .apply()
    Log.d("userId","UID is $userId, and it is $userIdIsValidate")
}

fun validateUid(userId: String): Boolean {
    val pattern = "^[A-Za-z]{2}\\d{3}$"
    // Step 2: Compile the regex
    val regex = pattern.toRegex()
    // Step 3: Check the string
    return regex.matches(userId)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UseIdInputField(
    showDialog: MutableState<Boolean>
) {
    var userId by remember { mutableStateOf("") }
    val context = LocalContext.current
    TextField(
        value = userId,
        onValueChange = { userId = it },
        label = { Text("User ID") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                showDialog.value = false
                saveUserId(
                    userId,
                    context
                )
            }
        )
    )
    Button(
        onClick = {
            showDialog.value = false
            saveUserId(
                userId,
                context
            )
        }
    ){
        Text("Confirm")
    }
}