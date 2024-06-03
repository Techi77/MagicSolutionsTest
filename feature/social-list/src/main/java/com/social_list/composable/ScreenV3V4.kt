package com.social_list.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.R
import com.core.ui.composable.Content
import com.core.ui.composable.MagicMenu
import com.core.ui.composable.MagicMenuItem
import com.core.ui.composable.input_link.GlobalInputLinkNew
import com.core.ui.model.Social
import com.core.ui.modifier.clickableSingle
import com.core.ui.theme.MagicDownloaderTheme
import com.social_list.SocialListEvent
import com.social_list.SocialListViewModel
import com.social_list.composable.tutorial.TutorialBoxWithText
import com.social_list.composable.tutorial.TutorialStepsDate
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ScreenV3V4(
    onClick: (SocialListEvent) -> Unit,
    viewModel: SocialListViewModel
) {
    val scrollState = rememberScrollState()
    var isShowLogo by remember { mutableStateOf(true) }

    var tutorialStep by remember { mutableIntStateOf(-1) }

    val stepFromViewModel by viewModel.tutorialStep.collectAsState()
    LaunchedEffect(stepFromViewModel) {
        tutorialStep = stepFromViewModel
    }

    val tutorialItemsParameters by viewModel.tutorialItemsParameters.collectAsState()


    var arrowSize by remember { mutableStateOf(IntSize(0, 0)) }

    var commonParentModifier = Modifier
        .fillMaxSize()
        .verticalScroll(state = scrollState)
        .statusBarsPadding()
    if (tutorialStep in 0..3) {
        val viewModelSize = tutorialItemsParameters[tutorialStep]?.size ?: Pair(0f, 0f)
        val viewModelOffset = tutorialItemsParameters[tutorialStep]?.offset ?: Pair(0f, 0f)
        val tutorialBoxOffset = Offset(viewModelOffset.first, viewModelOffset.second)
        val tutorialBoxSize = Size(viewModelSize.first, viewModelSize.second)
        val tutorialBoxCornerRadius =
            tutorialItemsParameters[tutorialStep]?.cornerRadius?.dp ?: 0.dp
        commonParentModifier =
            commonParentModifier.addTutorialToLayout(
                if(tutorialStep!=3)tutorialBoxOffset else Offset(0f,0f),
                if(tutorialStep!=3)tutorialBoxSize else Size(0f,0f),
                if(tutorialStep!=3)tutorialBoxCornerRadius else 0.dp
            )
    }


    commonParentModifier = commonParentModifier.then(Modifier.padding(horizontal = 16.dp))


    Content {
        val toolbar = WindowInsets.systemBars.asPaddingValues().calculateTopPadding().dpToPx()
        Box {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = commonParentModifier
            ) {
                Row(
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        contentAlignment = Alignment.TopStart,
                        modifier = Modifier
                            .width(intrinsicSize = IntrinsicSize.Min)
                            .height(intrinsicSize = IntrinsicSize.Min)
                            .padding(all = 4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_russian_flag),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(size = 42.dp)
                                .padding(all = 6.dp)
                                .border(
                                    width = 2.dp,
                                    shape = CircleShape,
                                    color = Color(0xFFFF9901)
                                )
                                .clip(CircleShape)
                                .onGloballyPositioned { coordinates ->
                                    if (tutorialItemsParameters[2] == null) {
                                        viewModel.setTutorialItemsParameters(
                                            index = 2,
                                            offset = Pair(
                                                coordinates.positionInRoot().x,
                                                coordinates.positionInRoot().y - toolbar
                                            ),
                                            size = getSizeByCoordinates(coordinates),
                                            cornerRadius = 24
                                        )
                                    }
                                }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(size = 48.dp)
                                .clip(shape = MaterialTheme.shapes.extraLarge)
                                .clickableSingle { }
                                .padding(all = 4.dp)
                                .padding(all = 6.dp)
                                .border(
                                    width = 2.dp,
                                    shape = CircleShape,
                                    color = Color(0xFFFF9901)
                                )
                        )

                        MagicMenu(
                            items = listOf(
                                MagicMenuItem.Tutorial to {
                                    viewModel.setTutorialStep(0)
                                },
                                MagicMenuItem.HomeManual to { },
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(height = 5.dp))

                Spacer(modifier = Modifier.weight(weight = 5f))

                if (isShowLogo) {
                    Logo()
                }

                Spacer(modifier = Modifier.height(height = 10.dp))

                Spacer(modifier = Modifier.weight(weight = 10f))

                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .height(intrinsicSize = IntrinsicSize.Min)
                ) {
                    GlobalInputLinkNew(
                        type = remember { mutableStateOf(Social.Websites) },
                        placeholderText = R.string.search_or_type_url,
                        readOnly = true,
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                if (tutorialItemsParameters[0] == null) {
                                    viewModel.setTutorialItemsParameters(
                                        index = 0,
                                        offset = Pair(
                                            coordinates.positionInRoot().x,
                                            coordinates.positionInRoot().y - toolbar
                                        ),
                                        size = getSizeByCoordinates(coordinates),
                                        cornerRadius = 50
                                    )
                                }
                            }
                    )
                }

                Spacer(modifier = Modifier.height(height = 14.dp))

                Spacer(modifier = Modifier.weight(weight = 10f))

                Text(
                    text = stringResource(id = R.string.or_download_from),
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight(500),
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(height = 10.dp))

                Spacer(modifier = Modifier.weight(weight = 6f))

                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .height(intrinsicSize = IntrinsicSize.Min)
                ) {
                    SocialsV4(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                if (tutorialItemsParameters[1] == null) {
                                    viewModel.setTutorialItemsParameters(
                                        index = 1,
                                        offset = Pair(
                                            coordinates.positionInRoot().x,
                                            coordinates.positionInRoot().y - toolbar
                                        ),
                                        size = getSizeByCoordinates(coordinates),
                                        cornerRadius = 24
                                    )
                                }
                            }
                    )
                }

                Spacer(modifier = Modifier.height(height = 16.dp))

                NativeAdView()
            }
            if (tutorialStep in 0..3) {
                val viewModelSize = tutorialItemsParameters[tutorialStep]?.size ?: Pair(0f, 0f)
                val viewModelOffset =
                    tutorialItemsParameters[tutorialStep]?.offset ?: Pair(0f, 0f)

                val tutorialBoxOffset = Offset(viewModelOffset.first, viewModelOffset.second)
                val tutorialBoxSize = Size(viewModelSize.first, viewModelSize.second)
                var tutorialDialogSize by remember { mutableStateOf(IntSize(0, 0)) }

                val configuration = LocalConfiguration.current
                val screenHeight = configuration.screenHeightDp.dp
                val screenWidth = configuration.screenWidthDp.dp

                val tutorialBoxWithTextY = when (tutorialStep) {
                    0, 1 -> ((tutorialBoxOffset.y + tutorialBoxSize.height).pxToDp() + arrowSize.height.toFloat()
                        .pxToDp() + 20.dp + 22.dp + toolbar.pxToDp()).value

                    2 -> ((tutorialBoxOffset.y + tutorialBoxSize.height).pxToDp() + 124.dp + toolbar.pxToDp()).value
                    3 -> (screenHeight - (arrowSize.height + tutorialDialogSize.height).toFloat()
                        .pxToDp() - 20.dp - 22.dp - toolbar.pxToDp()).value

                    else -> 0f
                }
                val tutorialArrowX = when (tutorialStep) {
                    0, 1, 3 -> (screenWidth / 2 - arrowSize.width.toFloat().pxToDp() / 2).value
                    2 -> (tutorialBoxOffset.x + tutorialBoxSize.width).pxToDp().value
                    else -> 0f
                }
                val tutorialArrowY = when (tutorialStep) {
                    0, 1 -> ((tutorialBoxOffset.y + tutorialBoxSize.height).pxToDp() + 20.dp + toolbar.pxToDp()).value
                    2 -> (tutorialBoxOffset.y + tutorialBoxSize.height / 2 + toolbar).pxToDp().value
                    3 -> (screenHeight - arrowSize.height.toFloat()
                        .pxToDp() - 20.dp - toolbar.pxToDp()).value

                    else -> 0f
                }
                val tutorialStepDate = TutorialStepsDate.entries[tutorialStep]
                Image(
                    imageVector = ImageVector.vectorResource(id = tutorialStepDate.arrowRes),
                    contentDescription = null,
                    modifier = Modifier
                        .onGloballyPositioned {
                            arrowSize = it.size
                        }
                        .offset(
                            x = tutorialArrowX.dp,
                            y = tutorialArrowY.dp
                        )
                        .scale(
                            scaleX = if (tutorialStep == 3) -1f else 1f,
                            scaleY = if (tutorialStep == 3) -1f else 1f
                        ),
                )
                Box(modifier = Modifier
                    .onGloballyPositioned {
                        tutorialDialogSize = it.size
                    }
                    .offset(
                        x = (screenWidth / 2 - tutorialDialogSize.width
                            .toFloat()
                            .pxToDp() / 2),
                        y = tutorialBoxWithTextY.dp
                    )
                ) {
                    TutorialBoxWithText(tutorialStepDate) {
                        val isLastStep = tutorialStep == TutorialStepsDate.entries.lastIndex
                        viewModel.setTutorialStep(if (isLastStep) -1 else tutorialStep + 1)
                    }
                }
            }

        }

        LaunchedEffect(key1 = scrollState.maxValue) {
            if (scrollState.maxValue > 0) isShowLogo = false
        }
    }
}

@Composable
fun Float.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

private fun getSizeByCoordinates(coordinates: LayoutCoordinates) = Pair(
    coordinates.size.width.toFloat(),
    coordinates.size.height.toFloat()
)

private fun Modifier.addTutorialToLayout(boxOffset: Offset, boxSize: Size, boxCornerRadius: Dp) =
    this.then(Modifier.drawWithContent {
        drawContent()
        val roundedPath = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        offset = boxOffset,
                        size = boxSize,
                    ),
                    cornerRadius = CornerRadius(boxCornerRadius.toPx())
                )
            )
        }
        clipPath(roundedPath, clipOp = ClipOp.Difference) {
            drawRect(SolidColor(Color(0x99000000)))
        }
        val strokeWidth = 1.5F
        val stroke = Stroke(
            width = strokeWidth.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(6.dp.toPx(), 6.dp.toPx()), 0f)
        )
        drawRoundRect(
            color = Color.White,
            style = stroke,
            cornerRadius = CornerRadius(boxCornerRadius.toPx()),
            topLeft = Offset(boxOffset.x - strokeWidth, boxOffset.y - strokeWidth),
            size = Size(boxSize.width + strokeWidth * 2, boxSize.height + strokeWidth * 2)
        )
    })

@Preview(showBackground = true)
@Composable
private fun ScreenNewPreview() {
    MagicDownloaderTheme {
        ScreenV3V4({}, koinViewModel())
    }
}

