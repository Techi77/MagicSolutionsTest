package com.core.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.R
import com.core.ui.theme.MagicDownloaderTheme

@Composable
fun Toolbar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    Content {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            navigationIcon.invoke()

            Spacer(modifier = Modifier.width(width = 12.dp))

            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .weight(weight = 1f)
            ) {
                title.invoke()
            }

            Spacer(modifier = Modifier.width(width = 12.dp))

            actions.invoke(this)
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MagicDownloaderTheme {
        Toolbar(
            navigationIcon = {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(size = 32.dp)
                )
            },
            title = {
                Text(
                    text = stringResource(id = R.string.downloads),
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 19.8.sp,
                        fontWeight = FontWeight(600),
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
                    modifier = Modifier
                )
            },
            actions = {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_tutorial),
                    contentDescription = null,
                    modifier = Modifier
                )
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
}