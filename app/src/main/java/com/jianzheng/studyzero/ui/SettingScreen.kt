package com.jianzheng.studyzero.ui

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jianzheng.studyzero.R
import com.jianzheng.studyzero.navigation.NavigationDestination
import com.jianzheng.studyzero.service.OverlayService
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme

val mediumPadding = 12.dp

object SettingDestination : NavigationDestination {
    override val route = "setting"
    override val titleRes = 0
}
@Composable
fun SettingScreen(
    navigateToEsm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                Text("Lock and then unlock your phone to see the floating window")
                ShowStatus()
                //ShowOptions()
                TestButton(navigateToEsm)
            }
        }
    }

}

@Composable
fun TestButton(
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    val serviceIntent = Intent(context, OverlayService::class.java)
    Button(
        onClick = {
            onClick()
            Log.d("unlock","Test clicked")
        }
    ) {
        Text(
            text = "Test",
            style = MaterialTheme.typography.bodyLarge
        )

    }
}

@Composable
fun ShowStatus(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(mediumPadding)
    ) {
        Text("prompts showed: x")
        Text("prompts answered: y")
    }
}

@Composable
fun ShowOptions(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    )
    {
        Text("Data collection in progress ")
        Spacer(modifier = Modifier.weight(0.1f))
        SwitchMinimalExample()
    }
}


@Composable
fun SwitchMinimalExample() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
            if (checked) {
                Log.d("Setting", "Data collection ON")
                //context.registerReceiver(unlockReceiver, IntentFilter(Intent.ACTION_USER_PRESENT))
            } else {
                Log.d("Setting", "Data collection OFF")
                //context.unregisterReceiver(unlockReceiver)
            }
        }
    )
}

@Preview
@Composable
fun PreviewSetting() {
    StudyZeroTheme {
        // A surface container using the 'background' color from the theme
        Surface {
            SettingScreen({})
        }
    }
}

