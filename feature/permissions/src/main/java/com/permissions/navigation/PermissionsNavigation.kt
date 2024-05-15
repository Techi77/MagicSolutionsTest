package com.permissions.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.permissions.PermissionsScreen
import org.koin.androidx.compose.koinViewModel

private const val permissionsScreenRouteForNavigate = "permissionsScreen"
const val permissionsScreenRoute = "$permissionsScreenRouteForNavigate/{data}"

fun NavGraphBuilder.permissionsScreen(
    onNavigateToHome: () -> Unit,
) {
    composable(permissionsScreenRoute) {
        PermissionsScreen(
            viewModel = koinViewModel(),
            onNavigateToHome = onNavigateToHome,
        )
    }
}

fun NavController.navigateToPermissions(prevRout: String, data: Boolean = false) {
    navigate("$permissionsScreenRouteForNavigate/$data") {
        popUpTo(prevRout) {
            inclusive = true
        }
    }
}