package com.social_list.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.core.ui.utils.koinActivityViewModel
import com.social_list.SocialListScreen
import org.koin.androidx.compose.koinViewModel

const val socialListScreenRoute = "socialListScreen"

fun NavGraphBuilder.socialListScreen() {
    composable(socialListScreenRoute) {
        SocialListScreen(
            viewModel = koinViewModel(),
            mainViewModel = koinActivityViewModel(),
        )
    }
}