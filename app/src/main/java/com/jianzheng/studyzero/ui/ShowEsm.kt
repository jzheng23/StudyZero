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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jianzheng.studyzero.R
import com.jianzheng.studyzero.R.drawable.tw_bg
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme


@Composable
fun ShowEsm(
    isPreview: Boolean,
    modifier: Modifier = Modifier,
    myViewModel: EsmViewModel = viewModel()
) {
    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)
    val activity = if (isPreview) null else (LocalContext.current as Activity)
    Box {
        Image(
            painter = painterResource(id = tw_bg),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Dialog(
            onDismissRequest = {
                activity?.finish()
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
                        .padding( vertical = mediumPadding)
                ) {
                    Text(
                        stringResource(R.string.instruction),
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = LocalTextStyle.current.merge(
                            TextStyle(
                                lineHeight = 1.0.em,
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                ),
                                lineHeightStyle = LineHeightStyle(
                                    alignment = LineHeightStyle.Alignment.Center,
                                    trim = LineHeightStyle.Trim.None
                                )
                            )
                        ),
                        modifier = Modifier
                            .padding(horizontal = mediumPadding, vertical = mediumPadding / 2)
                    )
                    ShowQuestion(R.string.question1, onSelectionChanged = {myViewModel.setSelection1(it)})
                    ShowQuestion(R.string.question2, onSelectionChanged = {myViewModel.setSelection2(it)})
                    //Submit()
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = dimensionResource(id = R.dimen.padding_medium),
                                vertical = dimensionResource(id = R.dimen.padding_medium) / 2
                            ),
                        onClick = {activity?.finish()}) {
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

@Composable
fun ShowQuestion(
    questionStringID: Int,
    modifier: Modifier = Modifier,
    onSelectionChanged: (Int) -> Unit = {}
    ) {
    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)
    Card(
        //elevation = CardElevation() ,
        modifier = modifier
            .fillMaxWidth()
            //.wrapContentHeight()
            .padding(horizontal = mediumPadding, vertical = mediumPadding)
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id =  R.dimen.elevation))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = mediumPadding, vertical = mediumPadding / 4)
        ) {
            Text(
                stringResource(id = questionStringID),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = LocalTextStyle.current.merge(
                    TextStyle(
                        lineHeight = 1.0.em,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        ),
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.None
                        )
                    )
                ),
            )
            LikertButtons(onSelectionChanged = onSelectionChanged)
        }
    }
}

@Composable
fun TextLabel (text: String){
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudyZeroTheme {
        ShowEsm(isPreview = true)
    }
}

//@Preview(showBackground = true)
@Composable
fun GreetingPreviewDark() {
    StudyZeroTheme(darkTheme = true) {
        ShowEsm(isPreview = true)
    }
}

@Composable
fun LikertButtons(
    modifier: Modifier = Modifier,
    onSelectionChanged: (Int) -> Unit = {},
){
    val options: List<Int> = listOf(1,2,3,4,5)
    val labels: List<String> = listOf("strongly disagree","","neutral","","strongly agree")
    var selectedValue by rememberSaveable { mutableStateOf(0) }

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            //.height(28.dp)
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.padding_medium))
        ) {
        options.forEach { item ->
            Column {
                RadioButton(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    },
                    modifier = modifier
                        .height(22.dp)
                )
                Text(
                    text = labels[item - 1],
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center ,
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            lineHeight = 1.0.em,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ),
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.None
                            )
                        )
                    ),
                    modifier = modifier
                        .width(55.dp)

                )
            }
        }
    }
}
