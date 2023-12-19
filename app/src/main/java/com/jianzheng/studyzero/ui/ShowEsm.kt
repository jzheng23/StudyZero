package com.jianzheng.studyzero.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jianzheng.studyzero.R


@Composable
fun ShowEsm(
    modifier: Modifier = Modifier,
) {
    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)
    val myViewModel: EsmViewModel = viewModel()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(vertical = mediumPadding)
    ) {
        ShowInstruction()
        //show question 1
        ShowQuestion(
            questionString = R.string.question1,
            index = 0,
            myViewModel = myViewModel
        )
        ShowQuestion(
            questionString = R.string.question2,
            index = 1,
            myViewModel = myViewModel
        )
    }
}

@Composable
fun ShowInstruction(){
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
}

@Composable
fun ShowQuestion(
    questionString: Int,
    modifier: Modifier = Modifier,
    index: Int,
    myViewModel: EsmViewModel
){
    val options: List<Int> = listOf(1, 2, 3, 4, 5)
    val labels: List<String> = listOf("Strongly disagree", "", "Neutral", "", "Strongly agree")

    Card(
        modifier = modifier
            .fillMaxWidth()
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
                stringResource(questionString),
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
                    ChoiceWithLabel(
                        item = item,
                        label = labels[item -1],
                        index = index,
                        myViewModel = myViewModel
                    )
                }
            }
        }
    }
}
@Composable
fun ChoiceWithLabel(
    item: Int,
    label: String,
    index: Int,
    myViewModel: EsmViewModel,
    modifier: Modifier = Modifier,
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        RadioButton(
            selected = myViewModel.getAnswer(index) == item,
            onClick = { myViewModel.setAnswer(item, index) } ,
            modifier = modifier
                .height(22.dp)
        )
        Text(
            text = label,//s[item - 1],
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


