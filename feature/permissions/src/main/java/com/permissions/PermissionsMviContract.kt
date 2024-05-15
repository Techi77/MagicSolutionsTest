package com.permissions

import com.mvi.ScreenEvent
import com.mvi.ScreenSingleEvent
import com.mvi.ScreenState

data class PermissionsState(
    val image: Int,
    val text: Int,
    val subtext: Int,
    val buttonText: Int,
    val actionEvent: PermissionsEvent,
) : ScreenState

enum class PermissionsEvent : ScreenEvent {
    Next, Start, Skip,
}

enum class PermissionsSingleEvent : ScreenSingleEvent {
    NavigateToHome
}