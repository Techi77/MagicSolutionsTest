package com.core.ui.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.R
import com.core.ui.modifier.clickableSingle

enum class MagicMenuItem(
    @DrawableRes val icon: Int,
    @StringRes val text: Int,
) {
    Downloads(
        icon = R.drawable.ic_downloads,
        text = R.string.downloads,
    ),
    History(
        icon = R.drawable.ic_history,
        text = R.string.history,
    ),
    Home(
        icon = R.drawable.ic_home,
        text = R.string.home,
    ),
    Settings(
        icon = R.drawable.ic_setting,
        text = R.string.settings,
    ),
    Tutorial(
        icon = R.drawable.ic_tutorial,
        text = R.string.tutorial,
    ),
    HomeManual(
        icon = R.drawable.ic_tutorial,
        text = R.string.home_manual,
    ),
}

@Composable
fun MagicMenu(
    items: List<Pair<MagicMenuItem, () -> Unit>>,
    visible: Boolean = false,
    textColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    iconColor: Color = textColor,
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(visible)
    }

    val menuIconModifier by remember {
        mutableStateOf(
            Modifier
                .padding(start = 16.dp)
        )
    }

    val menuTextModifier by remember {
        mutableStateOf(
            Modifier
                .widthIn(min = 120.dp)
                .padding(vertical = 4.dp)
                .padding(end = 24.dp)
        )
    }

    val menuTextStyle by remember {
        mutableStateOf(
            TextStyle(
                fontSize = 16.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight(600),
                color = textColor,
            )
        )
    }

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_browser_menu),
            contentDescription = null,
            modifier = Modifier
                .padding(all = 2.dp)
                .clip(shape = MaterialTheme.shapes.extraLarge)
                .clickableSingle(onClick = { isContextMenuVisible = true })
                .padding(all = 10.dp)
        )

        MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = MaterialTheme.shapes.extraLarge)) {
            DropdownMenu(
                expanded = isContextMenuVisible,
                onDismissRequest = { isContextMenuVisible = false },
//                offset = DpOffset.Zero.copy(y = (-4).dp),
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .padding(vertical = 4.dp)
            ) {
                items.forEach {
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = it.first.icon),
                                contentDescription = null,
                                tint = iconColor,
                                modifier = menuIconModifier
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(id = it.first.text),
                                style = menuTextStyle,
                                modifier = menuTextModifier
                            )
                        },
                        onClick = {
                            it.second.invoke()
                            isContextMenuVisible = false
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}