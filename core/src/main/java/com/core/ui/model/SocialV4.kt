package com.core.ui.model

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

sealed interface SocialV4 {
    val social: Social
    val icon: Int
    val title: Int
    val titleString: String?
    var backgroundColor: Color
    var backgroundGradient: Brush?

    class Instagram(
        override val social: Social = Social.Instagram,
        override val icon: Int = social.icon,
        override val title: Int = social.title,
        override val titleString: String? = social.titleString,
        override var backgroundColor: Color = social.backgroundColor,
        override var backgroundGradient: Brush? = social.backgroundGradient,
    ) : SocialV4

    class Facebook(
        override val social: Social = Social.Facebook,
        override val icon: Int = social.icon,
        override val title: Int = social.title,
        override val titleString: String? = social.titleString,
        override var backgroundColor: Color = social.backgroundColor,
        override var backgroundGradient: Brush? = social.backgroundGradient,
    ) : SocialV4

    class Twitter(
        override val social: Social = Social.Twitter,
        override val icon: Int = social.icon,
        override val title: Int = social.title,
        override val titleString: String? = social.titleString,
        override var backgroundColor: Color = social.backgroundColor,
        override var backgroundGradient: Brush? = social.backgroundGradient,
    ) : SocialV4

    class Websites(
        override val social: Social = Social.Websites,
        val link: String,
        override val icon: Int,
        override val title: Int,
        override var backgroundColor: Color,
        override val titleString: String? = null,
        override var backgroundGradient: Brush? = null,
    ) : SocialV4
}