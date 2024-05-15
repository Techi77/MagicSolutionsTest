package com.splash

import com.mvi.MviProcessor
import kotlinx.coroutines.delay

class SplashViewModel : MviProcessor<SplashState, SplashEvent, SplashSingleEvent>() {

    private var onResume: Boolean = false

    override fun initialState(): SplashState {
        return SplashState(
            progress = 0f,
            checked = true,
            showPrivacyPolicy = false,
        )
    }

    override fun reduce(event: SplashEvent, state: SplashState): SplashState {
        return when (event) {
            is SplashEvent.IncrementProgress -> state.copy(progress = state.progress + 0.001f)

            is SplashEvent.OnResume -> state.also { onResume = true }
            is SplashEvent.OnPause -> state.also { onResume = false }

            is SplashEvent.ShowPrivacyPolicy -> state.copy(showPrivacyPolicy = true)

            is SplashEvent.AgreePrivacyPolicy -> state

            is SplashEvent.OnCheckChanged -> state.copy(checked = !state.checked)
        }
    }

    override suspend fun handleEvent(event: SplashEvent, state: SplashState): SplashEvent? {
        return when (event) {
            is SplashEvent.OnResume -> {
                if (state.progress < 1f) SplashEvent.IncrementProgress
                else null
            }

            is SplashEvent.IncrementProgress -> {
                when {
                    state.progress < 1f && onResume -> {
                        delay(3)
                        SplashEvent.IncrementProgress
                    }

                    state.progress >= 1f -> SplashEvent.ShowPrivacyPolicy

                    else -> null
                }
            }

            is SplashEvent.AgreePrivacyPolicy -> {
                triggerSingleEvent(SplashSingleEvent.Permissions)
                null
            }

            is SplashEvent.OnPause,
            is SplashEvent.ShowPrivacyPolicy,
            is SplashEvent.OnCheckChanged,
            -> null
        }
    }
}