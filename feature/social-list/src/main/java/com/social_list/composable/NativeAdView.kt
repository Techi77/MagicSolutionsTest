package com.social_list.composable

import android.view.View
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
internal fun ColumnScope.NativeAdView() {
    NativeAdCard(
        modifier = Modifier
            .padding(bottom = 26.dp)
    )

    Spacer(
        modifier = Modifier.weight(
            weight = 5f
        )
    )
}

@Composable
fun NativeAdCard(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min)
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .background(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f))
            .animateContentSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
        )

        AndroidView(
            factory = { context ->
                View(context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 220.dp)
        )
    }
}