package com.jianzheng.studyzero.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jianzheng.studyzero.R
import com.jianzheng.studyzero.R.drawable.tw_bg
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme


@Composable
fun ShowEsm(modifier: Modifier = Modifier) {
    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)
    val activity = (LocalContext.current as Activity)
    Box {
        Image(
            painter = painterResource(id = tw_bg),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Dialog(
            onDismissRequest = {
                activity.finish()
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth(),
                //.wrapContentHeight()
                //.padding(horizontal = mediumPadding, vertical = mediumPadding / 2)
                //.background(color = MaterialTheme.colorScheme.background),
                elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.elevation))
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .background(color = MaterialTheme.colorScheme.surface)
                ) {
                    Text(
                        stringResource(R.string.instruction),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(horizontal = mediumPadding, vertical = mediumPadding / 2)
                    )
                    ShowQuestion(R.string.question1)
                    ShowQuestion(R.string.question2)
                    //Submit()
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = dimensionResource(id = R.dimen.padding_medium),
                                vertical = dimensionResource(id = R.dimen.padding_medium) / 2
                            ),
                        onClick = {activity.finish()}) {
                        Text(
                            stringResource(R.string.submit),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
        }
    }

}
/*
@Composable
fun Submit() {
    val activity = (LocalContext.current as Activity)
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
                vertical = dimensionResource(id = R.dimen.padding_medium) / 2
            ),
        onClick = {activity.finish()}) {
        Text(
            stringResource(R.string.submit),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

 */

@Composable
fun ShowQuestion(questionStringID: Int, modifier: Modifier = Modifier) {
    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)
    var sliderPosition by remember { mutableStateOf(1f) }
    Card(
        //elevation = CardElevation() ,
        modifier = modifier
            .fillMaxWidth()
            //.wrapContentHeight()
            .padding(horizontal = mediumPadding, vertical = mediumPadding / 2)
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id =  R.dimen.elevation))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = mediumPadding, vertical = mediumPadding / 2)
        ) {
            Text(
                stringResource(id = questionStringID),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.primaryContainer,
                    activeTickColor = MaterialTheme.colorScheme.primary,
                    inactiveTickColor = MaterialTheme.colorScheme.primary
                ),
                steps = 3,
                valueRange = 1f..5f
            )
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TextLabel("strongly")
                    TextLabel("disagree")
                }
                Spacer(modifier = Modifier.weight(1f))
                TextLabel("neutral")
                Spacer(modifier = Modifier.weight(1f))
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TextLabel("strongly")
                    TextLabel("agree")
                }
            }
        }
    }
}

@Composable
fun TextLabel (text: String){
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudyZeroTheme {
        ShowEsm()
    }
}

//@Preview(showBackground = true)
@Composable
fun GreetingPreviewDark() {
    StudyZeroTheme(darkTheme = true) {
        ShowEsm()
    }
}