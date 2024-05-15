package com.home.naviagtion

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.home.HomeScreen

internal const val homeScreenRoute = "homeScreen"

fun NavGraphBuilder.homeScreen(parentNavController: NavController) {
    composable(homeScreenRoute) {
        HomeScreen(parentNavController)
    }
}

fun NavController.navigateToHome(prevRout: String) {
    navigate(homeScreenRoute) {
        popUpTo(prevRout) {
            inclusive = true
        }
    }
}