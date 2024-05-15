package com.home.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.social_list.navigation.socialListScreen

internal fun NavGraphBuilder.homeNavGraph(navController: NavController, parentNavController: NavController) {
    socialListScreen()
}