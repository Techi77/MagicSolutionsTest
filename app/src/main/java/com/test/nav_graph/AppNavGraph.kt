package com.test.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.home.naviagtion.homeScreen
import com.home.naviagtion.navigateToHome
import com.permissions.navigation.navigateToPermissions
import com.permissions.navigation.permissionsScreen
import com.permissions.navigation.permissionsScreenRoute
import com.splash.navigation.splashScreen
import com.splash.navigation.splashScreenRoute

fun NavGraphBuilder.appNavGraph(navController: NavController) {
    splashScreen(
        onNavigateToHome = { navController.navigateToHome(splashScreenRoute) },
        onNavigateToPermissions = { navController.navigateToPermissions(splashScreenRoute) },
    )

    permissionsScreen(onNavigateToHome = { navController.navigateToHome(permissionsScreenRoute) })

    homeScreen(parentNavController = navController)
}