package com.home.bottom_bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.core.ui.theme.MagicDownloaderTheme
import kotlinx.coroutines.flow.flow

@Composable
internal fun AppBottomBar(
    navController: NavController,
    onClick: (item: AppBottomBarItem, currentRoute: String?) -> Unit,
) {
    BottomBarBase(
        navController = navController,
        tabs = flow { emit(listOf(1)) },
        isVisible = true,
        onClick = onClick,
    )
}

@Composable
internal fun getSelectedRoute(currentRoute: String?): String? {
    return when (currentRoute) {
        AppBottomBarItem.SocialList.route -> AppBottomBarItem.SocialList.route
        AppBottomBarItem.DownloadList.route -> AppBottomBarItem.DownloadList.route
        AppBottomBarItem.Setting.route -> AppBottomBarItem.Setting.route

        else -> currentRoute
    }
}

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