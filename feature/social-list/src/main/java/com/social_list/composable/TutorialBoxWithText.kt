package com.social_list.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.R
import com.core.ui.composable.ActionButton

@Composable
fun TutorialBoxWithText(isLast: Boolean = false) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .background(
                Color.White,
                shape = RoundedCornerShape(28.dp)
            )
            .height(intrinsicSize = IntrinsicSize.Min)
            .widthIn(min = 200.dp, max = 330.dp)
            .padding(all = 24.dp)
    ) {
        Text(
            text = "Download videos from social media & popular sites hassle-free!",
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(600),
                textAlign = TextAlign.Center,

                ),
        )
        ActionButton(
            text = stringResource(id = if (isLast) R.string.get_started else R.string.next),
            textStyle = TextStyle(
                fontSize = 16.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center,
            ),
            contentPadding = PaddingValues(horizontal = 24.dp),
            onClick = {},
            maxLines = 1,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .widthIn(min = 100.dp, max = 194.dp)
                .fillMaxWidth()
                .height(40.dp)
        )
    }
}

@Preview
@Composable
fun MyViewPreview() {
    TutorialBoxWithText()
}