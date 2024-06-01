package com.social_list.composable.tutorial

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.core.ui.modifier.clickableSingle

@Composable
fun TutorialDottedBox(cornerRadius: Dp, height: Dp? = null, width: Dp? = null) {
    var dottedBoxModifier = Modifier
        .clickableSingle(ripple = false) { }
        .drawWithCache {
            onDrawBehind {
                val stroke = Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )

                drawRoundRect(
                    color = Color.Gray,
                    style = stroke,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
    dottedBoxModifier = dottedBoxModifier.then(if(height!=null) Modifier.height(height) else Modifier.fillMaxHeight())
    dottedBoxModifier = dottedBoxModifier.then(if(width!=null) Modifier.width(width) else Modifier.fillMaxWidth())
    Box(
        modifier = dottedBoxModifier
    )
}