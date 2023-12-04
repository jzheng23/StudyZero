package com.jianzheng.studyzero.service

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.WindowManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.jianzheng.studyzero.R
import com.jianzheng.studyzero.cycle.MyLifecycleOwner
import com.jianzheng.studyzero.ui.ShowEsm
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme

class OverlayService : Service() {

    private val windowManager get() = getSystemService(WINDOW_SERVICE) as WindowManager

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_StudyZero)
        showOverlay()
    }

    private fun showOverlay() {
        val layoutFlag: Int =
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            // https://developer.android.com/reference/android/view/WindowManager.LayoutParams
            // alt: WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            // WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSPARENT
        )

        val composeView = ComposeView(this)
        composeView.setContent {
            Overlay(
                onClick = {
                    //dismissWindow()
                    windowManager.removeView(composeView)
                    stopSelf()
                }
            )
        }

        // Trick The ComposeView into thinking we are tracking lifecycle
        val viewModelStoreOwner = object : ViewModelStoreOwner {
            override val viewModelStore: ViewModelStore
                get() = ViewModelStore()
        }
        val lifecycleOwner = MyLifecycleOwner()
        lifecycleOwner.performRestore(null)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        composeView.setViewTreeLifecycleOwner(lifecycleOwner)
        composeView.setViewTreeViewModelStoreOwner(viewModelStoreOwner)
        composeView.setViewTreeSavedStateRegistryOwner(lifecycleOwner)

        // This is required or otherwise the UI will not recompose
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        windowManager.addView(composeView, params)
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
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
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally ,
//                modifier = Modifier
//                    //.padding(16.dp)
//                    .wrapContentSize()
//            ) {
////                var showHello by remember { mutableStateOf(true) }
////                if (showHello) {
////                    Text(text = "Hello from Compose")
////                }
//
//            }
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