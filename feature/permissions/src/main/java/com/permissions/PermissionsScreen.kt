package com.permissions

import android.Manifest
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.composable.ActionButton
import com.core.ui.composable.Content
import com.core.ui.modifier.clickableSingle
import com.core.ui.theme.MagicDownloaderTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun PermissionsScreen(
    viewModel: PermissionsViewModel,
    onNavigateToHome: () -> Unit,
) {
    Screen(
        state = viewModel.uiState.value,
        onClick = { viewModel.sendEvent(it) }
    )

    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.singleEvent.onEach {
                onNavigateToHome.invoke()
            }.collect()
        }
    )

    BackHandler {}
}

@Composable
private fun Screen(
    state: PermissionsState,
    onClick: (PermissionsEvent) -> Unit,
) {
    var postNotificationCancelCount by remember { mutableIntStateOf(0) }

    val postNotification = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            when {
                it -> onClick.invoke(state.actionEvent)

                else -> {
                    ++postNotificationCancelCount

                    if (postNotificationCancelCount >= 2) {
                        onClick.invoke(state.actionEvent)
                    }
                }
            }
        },
    )

    Content {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onPrimary)
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Text(
                text = stringResource(id = com.core.R.string.skip),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 17.6.sp,
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                ),
                modifier = Modifier
                    .padding(all = 10.dp)
                    .clip(shape = CircleShape)
                    .clickableSingle { onClick.invoke(PermissionsEvent.Skip) }
                    .padding(all = 6.dp)
                    .align(alignment = Alignment.End)
            )

            Spacer(modifier = Modifier.weight(weight = 7f))

            Image(
                bitmap = ImageBitmap.imageResource(id = state.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.weight(weight = 5f))

            Text(
                text = stringResource(id = state.text),
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(height = 20.dp))

            Text(
                text = stringResource(
                    id = state.subtext,
                    stringResource(id = com.core.R.string.app_name).trim()
                ),
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(height = 32.dp))

            ActionButton(
                text = state.buttonText,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 21.sp,
                    fontWeight = FontWeight(600),
                    textAlign = TextAlign.Center,
                ),
                contentPadding = PaddingValues(horizontal = 32.dp),
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        postNotification.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(height = 50.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    MagicDownloaderTheme {
        Screen(
            state = PermissionsState(
                image = com.core.R.drawable.img_onb_notifi_perm,
                text = com.core.R.string.text_onb_notifi,
                subtext = com.core.R.string.subtext_onb_notifi,
                buttonText = com.core.R.string.enable_notifications,
                actionEvent = PermissionsEvent.Next,
            )
        ) {}
    }
}