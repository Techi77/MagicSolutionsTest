package com.core.ui.composable.input_link

import android.util.Patterns
import android.webkit.URLUtil
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.R
import com.core.ui.composable.LifecycleListener
import com.core.ui.model.Social
import com.core.ui.modifier.clickableSingle
import com.core.ui.theme.MagicDownloaderTheme

private var lastCotyText: String? = ""

@Composable
fun GlobalInputLinkNew(
    type: State<Social>,
    placeholderText: Int,
    modifier: Modifier = Modifier,
    defaultValue: String = "",
    readOnly: Boolean = false,
    arrowTint: Color = MaterialTheme.colorScheme.primary,
    colors: TextFieldColors = TextFieldDefaults.colors(
//        focusedTextColor = MaterialTheme.colorScheme.onBackground,
        focusedContainerColor = Color(0xFFC7E5FA),
        focusedIndicatorColor = Color.Transparent,
        focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,

//        unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
        unfocusedContainerColor = Color(0xFFC7E5FA),
        unfocusedIndicatorColor = Color.Transparent,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),

//        errorTextColor = MaterialTheme.colorScheme.error,
        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
        errorIndicatorColor = Color.Transparent,
        errorLeadingIconColor = MaterialTheme.colorScheme.onError,
    ),
    onValueChange: (String) -> Unit = {},
    onValidValue: (String, Boolean) -> Unit = { _, _ -> },
    onClickSend: ((String) -> Unit)? = null,
) {
    InputLink(
        type = type,
        defaultValue = defaultValue,
        placeholderText = placeholderText,
        modifier = modifier,
        readOnly = readOnly,
        arrowTint = arrowTint,
        colors = colors,
        onValueChange = onValueChange,
        onValidValue = onValidValue,
        onClickSend = onClickSend,
    )
}

@Composable
private fun InputLink(
    type: State<Social>,
    placeholderText: Int,
    modifier: Modifier = Modifier,
    defaultValue: String = "",
    readOnly: Boolean = false,
    arrowTint: Color = MaterialTheme.colorScheme.primary,
    colors: TextFieldColors = TextFieldDefaults.colors(
//        focusedTextColor = MaterialTheme.colorScheme.onBackground,
        focusedContainerColor = Color(0xFFC7E5FA),
        focusedIndicatorColor = Color.Transparent,
        focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,

//        unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
        unfocusedContainerColor = Color(0xFFC7E5FA),
        unfocusedIndicatorColor = Color.Transparent,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),

//        errorTextColor = MaterialTheme.colorScheme.error,
        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
        errorIndicatorColor = Color.Transparent,
        errorLeadingIconColor = MaterialTheme.colorScheme.onError,
    ),
    onValueChange: (String) -> Unit = {},
    onValidValue: (String, Boolean) -> Unit = { _, _ -> },
    onClickSend: ((String) -> Unit)? = null,
) {
    val typeValue by remember(key1 = type) { type }

    var value by remember(key1 = typeValue.isClearUrl) {
        mutableStateOf(TextFieldValue(defaultValue))
    }

    val isNotError =
        when {
            typeValue.baseUrl.isEmpty() -> true
            value.text.isEmpty() -> true
            else -> value.text.isValidURL(typeValue.baseUrl)
        }

    onValidValue.invoke(value.text, isNotError && value.text.isNotEmpty())

    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    var copyText by remember {
        mutableStateOf<String?>(null)
    }

    var refresh by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current

    LinkTextField(
        value = value,
        onValueChange = {
            value = if (it.text.length - value.text.length > 2) {
                val url = findUrl(it.text)
                TextFieldValue(url, TextRange(url.length))
            } else {
                it
            }

            onValueChange.invoke(it.text)
        },
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onClickSend?.invoke(value.text)
            }
        ),
        isError = !isNotError,
        readOnly = readOnly,
        leadingIcon = {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_g),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        },
        placeholder = {
            Text(
                text = stringResource(
                    if (typeValue != Social.Instagram) placeholderText
                    else R.string.insert_link_to_reels,
                    stringResource(R.string.reels).replaceFirstChar { it.uppercase() }
                ),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(500),
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                modifier = Modifier
            )
        },
        trailingIcon = {
            when {
                value.text.isNotEmpty() && (onClickSend == null || !isNotError) -> Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_clear),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clip(shape = CircleShape)
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .clickableSingle {
                            value = TextFieldValue("")
                        }
                        .padding(all = 14.4.dp)
                )

                !readOnly && copyText != lastCotyText && copyText?.isValidURL(typeValue.baseUrl) == true && value.text.isEmpty() -> Text(
                    text = stringResource(id = R.string.paste),
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(600),
                        color = MaterialTheme.colorScheme.onPrimary,
                    ),
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clip(shape = CircleShape)
                        .background(color = MaterialTheme.colorScheme.primary)
                        .clickableSingle {
                            val temp = findUrl(copyText)

                            lastCotyText = temp
                            onValueChange.invoke(temp)
                            value = TextFieldValue(temp, TextRange(temp.length))
                        }
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                )

                typeValue != Social.Websites -> {}

                else -> Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = arrowTint,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clip(shape = CircleShape)
                        .clickableSingle { onClickSend?.invoke(value.text) }
                        .padding(all = 8.dp)
                )
            }
        },
        colors = colors,
        modifier = modifier
            .fillMaxWidth()
    )

    LifecycleListener(
        onStart = { lastCotyText = "" },
        onResume = { refresh = !refresh },
    )

    LaunchedEffect(
        key1 = refresh,
        block = { copyText = clipboardManager.getText()?.text }
    )
}

private fun String.isValidURL(typeUrl: List<String>): Boolean {
    val url = findUrl(this)
    val contains = typeUrl
        .map { url.contains(it) }
        .contains(true)

    return contains && URLUtil.isValidUrl(url) && Patterns.WEB_URL.matcher(url).matches()
}

private fun findUrl(text: String?): String {
    return when (text) {
        null -> ""
        else -> Regex(Patterns.WEB_URL.pattern())
            .findAll(text)
            .map { it.value }
            .toList()
            .firstOrNull() ?: text
    }
}

@Preview
@Composable
private fun Preview() {
    MagicDownloaderTheme {
        InputLink(
            type = remember { mutableStateOf(Social.Websites) },
            placeholderText = R.string.search_or_type_url,
            readOnly = true,
        )
    }
}