package com.core.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.modifier.MultipleEventsCutter
import com.core.ui.theme.MagicDownloaderTheme

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier,
    icon: Int? = null,
    text: Int? = null,
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 21.sp,
        fontWeight = FontWeight(600),
    ),
    enabled: Boolean = true,
    height: Dp = 64.dp,
    shape: Shape = MaterialTheme.shapes.large,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.secondary,
        disabledContentColor = MaterialTheme.colorScheme.onSecondary,
    ),
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        modifierIcon = modifierIcon,
        icon = icon,
        text = text?.let { stringResource(it) },
        textStyle = textStyle,
        enabled = enabled,
        height = height,
        shape = shape,
        colors = colors,
        contentPadding = contentPadding,
        onClick = onClick,
    )
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier,
    icon: Int? = null,
    text: String,
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 21.sp,
        fontWeight = FontWeight(600),
    ),
    enabled: Boolean = true,
    height: Dp = 64.dp,
    shape: Shape = MaterialTheme.shapes.large,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.secondary,
        disabledContentColor = MaterialTheme.colorScheme.onSecondary,
    ),
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        modifierIcon = modifierIcon,
        icon = icon,
        text = text,
        textStyle = textStyle,
        enabled = enabled,
        height = height,
        shape = shape,
        colors = colors,
        contentPadding = contentPadding,
        onClick = onClick,
    )
}

@Composable
private fun Button(
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier,
    icon: Int? = null,
    text: String? = null,
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 21.sp,
        fontWeight = FontWeight(600),
    ),
    enabled: Boolean = true,
    height: Dp = 64.dp,
    shape: Shape = MaterialTheme.shapes.large,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.secondary,
        disabledContentColor = MaterialTheme.colorScheme.onSecondary,
    ),
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    onClick: () -> Unit,
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    val onMultipleClick = { multipleEventsCutter.processEvent(event = onClick) }

    TextButton(
        onClick = onMultipleClick,
        enabled = enabled,
        shape = shape,
        colors = colors,
        contentPadding = contentPadding,
        modifier = modifier
            .height(height = height)
    ) {
        icon?.let {
            Icon(
                imageVector = ImageVector.vectorResource(id = it),
                contentDescription = null,
                modifier = modifierIcon
            )
        }

        if (icon != null && text != null) {
            Spacer(modifier = Modifier.width(width = 8.dp))
        }

        text?.let {
            Text(
                text = it,
                style = textStyle,
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MagicDownloaderTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
        ) {
            Button(
                text = stringResource(id = com.core.R.string.downloads),
                icon = com.core.R.drawable.ic_download,
            ) {}

            Button(
                enabled = false,
                text = stringResource(id = com.core.R.string.downloads),
                icon = com.core.R.drawable.ic_download,
            ) {}
        }
    }
}