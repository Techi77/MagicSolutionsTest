@file:Suppress("unused")

package com.home.bottom_bar

import com.social_list.navigation.socialListScreenRoute

enum class AppBottomBarItem(
    val icon: Int,
    val title: Int,
    val route: String,
) {
    SocialList(
        icon = com.core.R.drawable.ic_home,
        title = com.core.R.string.home,
        route = socialListScreenRoute
    ),
    DownloadList(
        icon = com.core.R.drawable.ic_downloads,
        title = com.core.R.string.downloads,
        route = "downloadScreenRoute"
    ),
    History(
        icon = com.core.R.drawable.ic_history,
        title = com.core.R.string.history,
        route = "browserHistoryScreenRoute"
    ),
    Tabs(
        icon = com.core.R.drawable.ic_tabs,
        title = com.core.R.string.tabs,
        route = "browserTabsScreenRoute"
    ),
    Setting(
        icon = com.core.R.drawable.ic_setting,
        title = com.core.R.string.settings,
        route = "settingScreenRoute"
    ),
}