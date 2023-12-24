package com.jianzheng.studyzero.ui

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.room.Room
import com.jianzheng.studyzero.R
import com.jianzheng.studyzero.cycle.MyLifecycleOwner
import com.jianzheng.studyzero.data.EsmDatabase
import com.jianzheng.studyzero.data.Response
import com.jianzheng.studyzero.service.MyForegroundService
import com.jianzheng.studyzero.service.OverlayService
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun ShowOverlay(
    onClick: () -> Unit,
    lifecycleOwner: MyLifecycleOwner,
    windowManager: WindowManager,
    composeView: ComposeView,
    context: Context
) {
    val db = Room.databaseBuilder(context, EsmDatabase::class.java,"response_database").build()
    val responseDao = db.responseDao()
    val myViewModel = EsmViewModel(responseDao)
    val isShowing = remember { mutableStateOf(true) }
    val handler = Handler(Looper.myLooper()!!)
    val displayTimeMillis = 10000L
    handler.postDelayed({
        if (isShowing.value) {
            isShowing.value = false
            lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            windowManager.removeView(composeView)
            context.stopService(Intent(context, OverlayService::class.java))
        }

    }, displayTimeMillis) // 10 seconds delay

    StudyZeroTheme(isOverlay = true) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White.copy(alpha = 0.5f),
            onClick = onClick
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(mediumPadding)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = RoundedCornerShape(20.dp)
                    ),
                onClick = { },
                elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.elevation)),
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShowEsm(myViewModel)
                    SubmitButton(
                        myViewModel = myViewModel,
                        isShowing = isShowing,
                        onClick = onClick,
                        context = context
                    )
                }
            }
        }
    }
}

@Composable
fun SubmitButton(
    myViewModel: EsmViewModel,
    isShowing: MutableState<Boolean>,
    onClick: () -> Unit,
    context: Context
) {
    val coroutineScope = rememberCoroutineScope()
    val startingTime = System.currentTimeMillis()
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(mediumPadding),
        enabled = myViewModel.uiState.collectAsState().value.isAnswerValid,
        onClick = {
            coroutineScope.launch(Dispatchers.IO) {
                myViewModel.saveAnswer(startingTime)
                Log.d("data","coroutineScope runs")
                val myForegroundServiceIntent = Intent(context, MyForegroundService::class.java)
                ContextCompat.startForegroundService(context, myForegroundServiceIntent)
            }
            Log.d("data","Submit clicked")
            isShowing.value = false
            onClick()
        }) {
        Text(
            text = "Submit",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun ShowEsm(
    myViewModel: EsmViewModel,
    modifier: Modifier = Modifier,
) {
    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)
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
fun ShowInstruction() {
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
    myViewModel: EsmViewModel,
) {
    val options: List<Int> = listOf(1, 2, 3, 4, 5)
    val labels: List<String> = listOf("Strongly disagree", "", "Neutral", "", "Strongly agree")
    val selectedValue = remember { mutableStateOf(0) }
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
                        label = labels[item - 1],
                        index = index,
                        myViewModel = myViewModel,
                        selectedValue = selectedValue
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
    selectedValue: MutableState<Int>,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        RadioButton(
            selected = selectedValue.value == item,
            onClick =
            {
                selectedValue.value = item
                myViewModel.setAnswer(item, index)
                Log.d("viewModel", "$item selected for q$index!")
            },
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


