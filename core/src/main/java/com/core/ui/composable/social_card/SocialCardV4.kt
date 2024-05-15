package com.core.ui.composable.social_card

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.model.SocialV4
import com.core.ui.modifier.clickableSingle

@Composable
fun RowScope.SocialCardV4(
    item: SocialV4,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        item = item,
        modifier = modifier,
        onClick = onClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RowScope.Card(
    item: SocialV4,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickableSingle(onClick = onClick, key = item, ripple = false)
            .weight(weight = 1f)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = item.icon),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(shape = CircleShape)
                .clickableSingle(onClick = onClick, key = item)
                .let {
                    when (val brush = item.backgroundGradient) {
                        null -> it.background(color = item.backgroundColor)
                        else -> it.background(brush = brush)
                    }
                }
        )

        Spacer(modifier = Modifier.height(height = 4.dp))

        Text(
            text = item.titleString ?: stringResource(id = item.title),
            style = TextStyle(
                fontSize = 13.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight(600),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            ),
            maxLines = 1,
            modifier = Modifier
                .basicMarquee()
        )
    }
}