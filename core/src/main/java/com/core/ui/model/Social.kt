package com.core.ui.model

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

enum class Social(
    var icon: Int,
    var iconApp: Int,
    var title: Int,
    var titleString: String? = null,
    var backgroundColor: Color,
    var backgroundGradient: Brush? = null,
    val baseUrl: List<String>,
    var isClearUrl: Boolean = false,
    val isEnable: Boolean = true,
) {
    Instagram(
        icon = com.core.R.drawable.ic_instagram,
        iconApp = com.core.R.drawable.ic_app_instagram,
        title = com.core.R.string.instagram,
        backgroundColor = Color(0xFFFF4081),
        backgroundGradient = Brush.linearGradient(
            colors = listOf(
                Color(0xFF5B4FE9),
                Color(0xFF8F39CE),
                Color(0xFFD53692),
                Color(0xFFF75274),
                Color(0xFFFCBB45),
                Color(0xFFFBE18A)
            ),
        ),
        baseUrl = listOf("www.instagram.com", "instagram.com"),
    ),
    Twitter(
        icon = com.core.R.drawable.ic_x,
        iconApp = com.core.R.drawable.ic_app_twitter,
        title = com.core.R.string.x,
        backgroundColor = Color(0xFF000000),
        baseUrl = listOf("twitter.com", "x.com"),
    ),
    Facebook(
        icon = com.core.R.drawable.ic_facebook,
        iconApp = com.core.R.drawable.ic_app_facebook,
        title = com.core.R.string.facebook,
        backgroundColor = Color(0xFF2979FF),
        baseUrl = listOf("m.facebook.com", "facebook.com", "fb.watch"),
    ),
    Websites(
        icon = com.core.R.drawable.ic_websites,
        iconApp = com.core.R.drawable.ic_source,
        title = com.core.R.string.downloads,
        backgroundColor = Color(0xFF7C4DFF),
        baseUrl = emptyList(),
    );

    companion object {
        fun get(name: String): Social {
            return when (name) {
                Instagram.name -> Instagram
                Twitter.name -> Twitter
                Facebook.name -> Facebook
                else -> Websites
            }
        }
    }
}