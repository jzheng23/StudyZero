package com.jianzheng.studyzero.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.jianzheng.studyzero.R
import com.jianzheng.studyzero.cycle.MyLifecycleOwner
import com.jianzheng.studyzero.navigation.NavigationDestination
import com.jianzheng.studyzero.service.OverlayService
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme

object EsmDestination : NavigationDestination {
    override val route = "esm"
    override val titleRes = 1
}

@Composable
fun EsmScreen(
    navigateBack: () -> Unit,
    windowManager: WindowManager,
    context: Context,
) {
    ShowOverlay(navigateBack,context)

//    val layoutFlag: Int =
//        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//
//    val params = WindowManager.LayoutParams(
//        WindowManager.LayoutParams.MATCH_PARENT,
//        WindowManager.LayoutParams.MATCH_PARENT,
//        layoutFlag,
//        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//        PixelFormat.TRANSLUCENT
//    )
//
//    val composeView = ComposeView(context)
//    val lifecycleOwner = MyLifecycleOwner()
//
//
//
//    val viewModelStoreOwner = object : ViewModelStoreOwner {
//        override val viewModelStore: ViewModelStore
//            get() = ViewModelStore()
//    }
//    lifecycleOwner.performRestore(null)
//    lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    composeView.setViewTreeLifecycleOwner(lifecycleOwner)
//    composeView.setViewTreeViewModelStoreOwner(viewModelStoreOwner)
//    composeView.setViewTreeSavedStateRegistryOwner(lifecycleOwner)
////
////     This is required or otherwise the UI will not recompose
//    lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START)
//    lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
//
//    composeView.setContent {
//        ShowOverlay(
//            onClick = {
//                lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//                windowManager.removeView(composeView)
//                navigateBack()
//            },
////            lifecycleOwner = lifecycleOwner,
////            windowManager = windowManager,
////            composeView = composeView,
////            context = context
//        )
//        Log.d("unlock","composeview set content")
//    }
//
//    windowManager.addView(composeView, params)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowOverlay(
    onClick: () -> Unit,
//    lifecycleOwner: MyLifecycleOwner,
//    windowManager: WindowManager,
//    composeView: ComposeView,
    context: Context
) {
    val myViewModel: EsmViewModel = viewModel()
//    val isShowing = remember { mutableStateOf(false) }


    Log.d("unlock","ShowOverlay Called")
//    val handler = Handler(Looper.myLooper()!!)
//    val displayTimeMillis = 10000L
//    handler.postDelayed({
//        if (isShowing.value) {
//            isShowing.value = false
//            lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//            windowManager.removeView(composeView)
//            context.stopService(Intent(context, OverlayService::class.java))
//        }
//
//    }, displayTimeMillis) // 10 seconds delay


    StudyZeroTheme(isOverlay = true) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White.copy(alpha = 0.5f),
            onClick = { onClick() }
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
//                        isShowing = isShowing,
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
//    isShowing: MutableState<Boolean>,
    onClick: () -> Unit,
    context: Context
) {
    val activity = (LocalContext.current as? Activity)
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(mediumPadding),
        enabled = myViewModel.uiState.collectAsState().value.isAnswerValid,
        onClick = {
//            isShowing.value = false
            onClick()
            activity?.finish()
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
//    var selectedValue = 0
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
//    selectedValue: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        RadioButton(
            selected = selectedValue.value == item,
//            selected = selectedValue == item,
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


