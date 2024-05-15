package com.splash

import com.mvi.ScreenEvent
import com.mvi.ScreenSingleEvent
import com.mvi.ScreenState

data class SplashState(
    val progress: Float,
    val checked: Boolean,
    val showPrivacyPolicy: Boolean,
) : ScreenState

sealed class SplashEvent : ScreenEvent {
    object OnResume: SplashEvent()
    object OnPause: SplashEvent()

    object IncrementProgress: SplashEvent()

    object ShowPrivacyPolicy: SplashEvent()
    object AgreePrivacyPolicy: SplashEvent()

    class OnCheckChanged(val value: Boolean): SplashEvent()
}

enum class SplashSingleEvent : ScreenSingleEvent {
    Permissions, Home
}