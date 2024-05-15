package com.mvi.main_mvi

import android.app.Activity
import android.content.Context
import com.mvi.ScreenEvent
import com.mvi.ScreenSingleEvent
import com.mvi.ScreenState

class MainState : ScreenState

sealed interface MainEvent : ScreenEvent {
    class CreateReviewManager(val context: Context) : MainEvent
    class RateUp(val activity: Activity?, val isOnlyReview: Boolean = true, val onComplete: () -> Unit = {}) :
        MainEvent

    class Share(val context: Context, val appName: String, val share: String) : MainEvent
}

sealed interface MainSingleEvent : ScreenSingleEvent