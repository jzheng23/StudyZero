package com.jianzheng.studyzero.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jianzheng.studyzero.R
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme


@Composable
fun ShowEsm(
    modifier: Modifier = Modifier,
    myViewModel: EsmViewModel = viewModel()
) {
    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)
    var selectedValue1 by remember { mutableStateOf(0) }
    var selectedValue2 by remember { mutableStateOf(0) }
    val options: List<Int> = listOf(1, 2, 3, 4, 5)
    val labels: List<String> = listOf("Strongly disagree", "", "Neutral", "", "Strongly agree")

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(vertical = mediumPadding)
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
//                    ShowQuestion(R.string.question1)
//                    ShowQuestion(R.string.question2)
        //show question 1
        Card(
            //elevation = CardElevation() ,
            modifier = modifier
                .fillMaxWidth()
                //.wrapContentHeight()
                .padding(horizontal = mediumPadding, vertical = mediumPadding),
            elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.elevation))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = mediumPadding, vertical = mediumPadding / 4)
            ) {
                Text(
                    stringResource(id = R.string.question1),
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
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        //.height(28.dp)
                        .fillMaxWidth()
                        .padding(top = dimensionResource(id = R.dimen.padding_medium))
                ) {
                    options.forEach { item ->
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            RadioButton(
                                selected = selectedValue1 == item,
                                onClick = {
                                    selectedValue1 = item
                                    myViewModel.setSelection1(item)
                                },
                                modifier = modifier
                                    .height(22.dp)
                            )
                            Text(
                                text = labels[item - 1],
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
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
        }

        Card(
            //elevation = CardElevation() ,
            modifier = modifier
                .fillMaxWidth()
                //.wrapContentHeight()
                .padding(horizontal = mediumPadding, vertical = mediumPadding),
            elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.elevation))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = mediumPadding, vertical = mediumPadding / 4)
            ) {
                Text(
                    stringResource(id = R.string.question2),
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
                                selected = selectedValue2 == item,
                                onClick = {
                                    selectedValue2 = item
                                    myViewModel.setSelection2(item)
                                },
                                modifier = modifier
                                    .height(22.dp)
                            )
                            Text(
                                text = labels[item - 1],
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
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
        }
    }
}

@Composable
fun Overlay(
    onClick: () -> Unit
) {
    StudyZeroTheme(isOverlay = true) {
        Surface(color = Color.Transparent) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                //.wrapContentHeight()
                //.padding(horizontal = mediumPadding, vertical = mediumPadding / 2)
                //.background(color = MaterialTheme.colorScheme.background),
                elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.elevation)),
                shape = RoundedCornerShape(20.dp)
            ) {

                ShowEsm()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 8.dp)

                )
                {
                    Button(onClick = {
                        onClick()
                    }) {
                        Text(
                            text = "Dismiss",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.2f))
                    Button(onClick = {
                        onClick()
                    }) {
                        Text(
                            text = "Submit",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun OverlayPreview() {
    Overlay(
        onClick = {}
    )
}