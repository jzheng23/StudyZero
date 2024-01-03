package com.jianzheng.studyzero.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jianzheng.studyzero.R
import com.jianzheng.studyzero.ui.theme.StudyZeroTheme

@Preview
@Composable
fun TestingView() {
    StudyZeroTheme {
        // A surface container using the 'background' color from the theme
        Surface {
            Row(
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
