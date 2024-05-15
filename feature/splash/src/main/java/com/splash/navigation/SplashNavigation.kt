package com.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.core.ui.utils.koinActivityViewModel
import com.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel

const val splashScreenRoute = "splashScreen"

fun NavGraphBuilder.splashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToPermissions: () -> Unit,
) {
    composable(splashScreenRoute) {
        SplashScreen(
            viewModel = koinViewModel(),
            mainViewModel = koinActivityViewModel(),
            onNavigateToHome = onNavigateToHome,
            onNavigateToPermissions = onNavigateToPermissions,
        )
    }
}