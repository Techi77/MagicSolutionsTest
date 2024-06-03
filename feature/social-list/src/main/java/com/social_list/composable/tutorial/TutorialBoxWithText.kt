package com.social_list.composable.tutorial

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
fun TutorialBoxWithText(tutorialStep: TutorialStepsDate, onClick: ()-> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .background(
                Color.White,
                shape = RoundedCornerShape(28.dp)
            )
            .widthIn(min = 200.dp, max = 330.dp)
            .padding(all = 24.dp)
    ) {
        Text(
            text = stringResource(tutorialStep.textRes),
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(600),
                textAlign = TextAlign.Center,

                ),
        )
        ActionButton(
            text = stringResource(id = if (tutorialStep == TutorialStepsDate.entries.last()) R.string.get_started else R.string.next),
            textStyle = TextStyle(
                fontSize = 16.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center,
            ),
            contentPadding = PaddingValues(horizontal = 24.dp),
            onClick = onClick,
            maxLines = 1,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .widthIn(min = 100.dp, max = 194.dp)
                .fillMaxWidth()
                .height(40.dp)
        )
    }
}

enum class TutorialStepsDate(
    @DrawableRes val arrowRes: Int,
    @StringRes val textRes: Int,
) {
    STEP_0(R.drawable.ic_arrow_straight, R.string.tutorial_step_0),
    STEP_1(R.drawable.ic_arrow_straight, R.string.tutorial_step_1),
    STEP_2(R.drawable.ic_arrow_diagonal, R.string.tutorial_step_2),
    STEP_3(R.drawable.ic_arrow_straight, R.string.tutorial_step_3)
}

@Preview
@Composable
fun MyViewPreview() {
    TutorialBoxWithText(TutorialStepsDate.STEP_0){}
}