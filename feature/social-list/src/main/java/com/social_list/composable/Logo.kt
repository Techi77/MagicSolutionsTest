package com.social_list.composable

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.core.R

@Composable
internal fun Logo() {
    Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo_middle),
        contentDescription = null,
        modifier = Modifier
    )
}