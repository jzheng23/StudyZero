package com.jianzheng.studyzero.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jianzheng.studyzero.R
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme

@Composable
fun ShowOverlayPreview() {

    StudyZeroTheme(isOverlay = true) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White.copy(alpha = 0.5f),
//            onClick = onClick
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(mediumPadding),
//                onClick = { },
                elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.elevation)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Box {
                    DismissButton(
                        onDismiss = { },
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ShowEsmPreview()
                        SubmitButtonPreview()
                    }
                }
            }
        }
    }

}

@Composable
fun SubmitButtonPreview() {

}

@Composable
fun ShowEsmPreview() {

}

@Preview
@Composable
fun PreviewEsm() {
    StudyZeroTheme {
        // A surface container using the 'background' color from the theme
        Surface {
            ShowOverlayPreview()
        }
    }
}