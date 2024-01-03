package com.jianzheng.studyzero.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jianzheng.studyzero.R
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme

@Preview
@Composable
fun testingView(){
    StudyZeroTheme {
        // A surface container using the 'background' color from the theme
        Surface {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
//                ShowInstructionTest()

                Text(stringResource(R.string.instruction), modifier = Modifier.weight(1f))
                DismissButton(
                    onDismiss = { },
                    modifier = Modifier.width(40.dp)
                )
            }
        }
    }
}

@Composable
fun ShowInstructionTest() {
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
            .padding(top = mediumPadding / 2)
    )
}