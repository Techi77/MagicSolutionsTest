package com.home.bottom_bar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.core.ui.theme.MagicDownloaderTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
internal fun BottomBarBase(
    navController: NavController,
    tabs: Flow<List<Int>>,
    isVisible: Boolean,
    onClick: (item: AppBottomBarItem, currentRoute: String?) -> Unit,
) {
    val items: List<AppBottomBarItem> = listOf(
        AppBottomBarItem.SocialList,
        AppBottomBarItem.DownloadList,
        AppBottomBarItem.History,
        AppBottomBarItem.Tabs,
        AppBottomBarItem.Setting,
    )

    val routeList = items
        .map { it.route }
        .toMutableList()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.parent?.route ?: navBackStackEntry?.destination?.route
    val selectedRoute = getSelectedRoute(currentRoute)

    BottomBarBase(
        items = items,
        currentRoute = currentRoute,
        selectedRoute = selectedRoute,
        tabs = tabs,
        isVisible = routeList.contains(selectedRoute) && isVisible,
        onClick = onClick,
    )
}

@Composable
private fun BottomBarBase(
    items: List<AppBottomBarItem>,
    currentRoute: String?,
    selectedRoute: String?,
    tabs: Flow<List<Int>>,
    isVisible: Boolean,
    onClick: (item: AppBottomBarItem, currentRoute: String?) -> Unit,
) {
    val tabsValue by tabs.collectAsStateWithLifecycle(initialValue = emptyList())
    val heightAnimation by animateDpAsState(
        targetValue = if (isVisible) 60.dp else 0.dp,
        label = "BottomBarHeightAnimation",
    )

    val tutorialStep by remember { mutableIntStateOf(0) }

    var tutorialBoxOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    var tutorialBoxSize by remember { mutableStateOf(Size(0f, 0f)) }
    val tutorialBoxCornerRadius = 50.dp

    var commonParentModifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.onPrimary)
        .navigationBarsPadding()
        .background(color = MaterialTheme.colorScheme.background)
    if (tutorialStep in 1..4) commonParentModifier =
        commonParentModifier.addTutorialToLayout(tutorialBoxOffset, tutorialBoxSize, tutorialBoxCornerRadius)
    commonParentModifier = commonParentModifier.then(
        Modifier
            .padding(bottom = 1.dp)
            .height(height = heightAnimation)
            .padding(top = 1.dp)
            .background(color = MaterialTheme.colorScheme.onPrimary)
    )

    NavigationBar(
        containerColor = Color.Transparent,
        modifier = commonParentModifier
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = item.icon),
                            contentDescription = stringResource(id = item.title),
                            modifier = Modifier
                                .requiredHeight(height = 24.dp)
                        )
                        //if (isTutorialVisible && item == AppBottomBarItem.DownloadList) TutorialDottedBox(cornerRadius = 50.dp, height = 48.dp, width = 48.dp)

                        if (item == AppBottomBarItem.Tabs) {
                            val count = when {
                                tabsValue.size >= 99 -> "99"
                                else -> tabsValue.size.toString()
                            }

                            val textSize = when {
                                count.length > 1 -> 9.sp
                                else -> 10.sp
                            }

                            val xOffset = when {
                                count.length > 1 -> (-3).dp
                                else -> (-3.3).dp
                            }

                            Text(
                                text = count,
                                style = TextStyle(
                                    fontSize = textSize,
                                    lineHeight = textSize,
                                    fontWeight = FontWeight.Black,
                                    textAlign = TextAlign.Center,
                                ),
                                modifier = Modifier
                                    .requiredHeight(height = 24.dp)
                                    .wrapContentSize()
                                    .offset(x = xOffset, y = 2.dp)
                            )
                        }
                    }

                },
                label = null,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
                alwaysShowLabel = true,
                selected = selectedRoute == item.route,
                onClick = { onClick.invoke(item, currentRoute) },
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    if (tutorialStep == 4 && item == AppBottomBarItem.DownloadList) {
                        tutorialBoxSize = Size(
                            coordinates.size.height.toFloat()-5F,
                            coordinates.size.height.toFloat()-5F
                        )
                        tutorialBoxOffset = Offset(
                            coordinates.positionInRoot().x+coordinates.size.height.toFloat()/4,
                            4f
                        )

                    }
                },
            )
        }
    }
}

private fun Modifier.addTutorialToLayout(boxOffset: Offset, boxSize: Size, boxCornerRadius: Dp) =
    this.then(Modifier.drawWithContent {
        drawContent()
        val circlePath = Path().apply {
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
        clipPath(circlePath, clipOp = ClipOp.Difference) {
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
            topLeft = Offset(boxOffset.x-strokeWidth,boxOffset.y-strokeWidth),
            size = Size(boxSize.width+strokeWidth*2,boxSize.height+strokeWidth*2)
        )
    })

@Preview(heightDp = 100)
@Composable
private fun PreviewBase() {
    MagicDownloaderTheme {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            BottomBarBase(
                navController = rememberNavController(),
                tabs = flow { },
                isVisible = true,
                onClick = { _, _ -> },
            )
        }
    }
}