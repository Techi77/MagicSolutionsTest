package com.splash

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.composable.ActionButton
import com.core.ui.composable.Content
import com.core.ui.composable.LifecycleListener
import com.core.ui.modifier.shadow
import com.core.ui.theme.M3SysDarkOnSurfaceVariant
import com.core.ui.theme.MagicDownloaderTheme
import com.core.ui.theme.TransparencyWhite100
import com.core.ui.theme.VerticalGradientWhite50
import com.mvi.main_mvi.MainEvent
import com.mvi.main_mvi.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
internal fun SplashScreen(
    viewModel: SplashViewModel,
    mainViewModel: MainViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToPermissions: () -> Unit,
) {
    val context = LocalContext.current

    Screen(
        state = viewModel.uiState,
        onClick = viewModel::sendEvent,
    )

    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.singleEvent.onEach {
                when (it) {
                    SplashSingleEvent.Home -> onNavigateToHome.invoke()

                    SplashSingleEvent.Permissions -> {
                        when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            true -> onNavigateToPermissions.invoke()
                            else -> onNavigateToHome.invoke()
                        }
                    }
                }
            }.collect()
        }
    )

    LifecycleListener(
        onResume = { viewModel.sendEvent(SplashEvent.OnResume) },
        onPause = { viewModel.sendEvent(SplashEvent.OnPause) },
        onDispose = { mainViewModel.sendEvent(MainEvent.CreateReviewManager(context)) }
    )

    BackHandler {}
}

@Composable
private fun Screen(
    state: State<SplashState>,
    onClick: (SplashEvent) -> Unit,
) {
    Content {
        when {
            state.value.showPrivacyPolicy -> {
                val checked = remember(key1 = state) {
                    derivedStateOf {
                        state.value.checked
                    }
                }

                PrivacyPolicy(
                    checked = checked,
                    onClick = onClick,
                )
            }

            else -> {
                val progress = remember(key1 = state) {
                    derivedStateOf {
                        state.value.progress
                    }
                }

                Progress(
                    progress = progress,
                    titleModifier = Modifier,
                )
            }
        }
    }
}

@Composable
private fun Progress(
    progress: State<Float>,
    modifier: Modifier = Modifier,
    titleModifier: Modifier = Modifier,
) {
    val progressValue by remember(key1 = progress) { progress }

    Content {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            Spacer(modifier = Modifier.weight(weight = 1f))

            Image(
                imageVector = ImageVector.vectorResource(id = com.core.R.drawable.logo2),
                contentDescription = null,
                modifier = Modifier
                    .shadow(color = MaterialTheme.colorScheme.onBackground, alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(height = 26.dp))

            Text(
                text = stringResource(id = com.core.R.string.app_name).trim(),
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 26.4.sp,
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                modifier = titleModifier
            )

            Spacer(modifier = Modifier.weight(weight = 1f))

            LinearProgressIndicator(
                progress = { progressValue },
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primaryContainer,
                strokeCap = StrokeCap.Round,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .padding(horizontal = 120.dp)
                    .clip(shape = CircleShape)
            )

            Spacer(modifier = Modifier.height(height = 43.dp))
        }
    }
}

@Composable
private fun PrivacyPolicy(
    checked: State<Boolean>,
    onClick: (SplashEvent) -> Unit,
) {
    val checkedValue by remember(key1 = checked) { checked }

    Content {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = com.core.R.drawable.img_bg_splash),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 127.dp)
                        .background(brush = VerticalGradientWhite50)
                )

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = TransparencyWhite100)
                        .navigationBarsPadding()
                ) {
                    Text(
                        text = stringResource(id = com.core.R.string.welcome_to_video_downloader),
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontWeight = FontWeight(700),
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier
                            .padding(horizontal = 58.dp)
                    )

                    Spacer(modifier = Modifier.height(height = 20.dp))

                    ActionButton(
                        text = com.core.R.string.continue_text,
                        enabled = checkedValue,
                        onClick = { onClick.invoke(SplashEvent.AgreePrivacyPolicy) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 58.dp)
                    )

                    Spacer(modifier = Modifier.height(height = 20.dp))

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier
                            .padding(start = 48.dp, end = 58.dp)
                    ) {
                        Checkbox(
                            checked = checkedValue,
                            onCheckedChange = { onClick.invoke(SplashEvent.OnCheckChanged(it)) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary,
                                uncheckedColor = M3SysDarkOnSurfaceVariant,
                                checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                            )
                        )

                        PrivacyTos()
                    }

                    Spacer(modifier = Modifier.height(height = 8.dp))
                }
            }
        }
    }
}

@Composable
private fun PrivacyTos() {
    val tos = stringResource(id = com.core.R.string.tos)
    val privacyPolicy = stringResource(id = com.core.R.string.privacy_policy)

    val annotatedString = buildAnnotatedString {
        append(text = stringResource(id = com.core.R.string.by_clicking_continue))

        append(text = " ")

        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.Underline,
                fontSize = 15.sp,
                fontWeight = FontWeight(400),
                color = MaterialTheme.colorScheme.onSecondary,
            )
        ) {
            pushStringAnnotation(tag = privacyPolicy, annotation = privacyPolicy)
            append(text = privacyPolicy)
        }

        append(text = " ")
        append(text = stringResource(id = com.core.R.string.and))
        append(text = " ")

        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.Underline,
                fontSize = 15.sp,
                fontWeight = FontWeight(400),
                color = MaterialTheme.colorScheme.onSecondary,
            )
        ) {
            pushStringAnnotation(tag = tos, annotation = tos)
            append(text = tos)
        }
    }

    ClickableText(
        text = annotatedString,
        style = TextStyle(
            fontSize = 15.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight(400),
            color = MaterialTheme.colorScheme.onSecondary,
        ),
        onClick = { },
        modifier = Modifier
            .padding(top = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    MagicDownloaderTheme {
        Progress(
            progress = remember { mutableFloatStateOf(0.3f) },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPrivacyPolicyPreview() {
    MagicDownloaderTheme {
        PrivacyPolicy(
            checked = remember { mutableStateOf(false) },
            onClick = {}
        )
    }
}