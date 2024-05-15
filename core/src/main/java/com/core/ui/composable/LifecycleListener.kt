package com.core.ui.composable

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
inline fun LifecycleListener(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    crossinline onCreate: () -> Unit = {},
    crossinline onStart: () -> Unit = {},
    crossinline onResume: () -> Unit = {},
    crossinline onPause: () -> Unit = {},
    crossinline onStop: () -> Unit = {},
    crossinline onDestroy: () -> Unit = {},
    crossinline onAny: () -> Unit = {},
    crossinline onDispose: () -> Unit = {},
) {
    val currentOnCreate by rememberUpdatedState { onCreate.invoke() }
    val currentOnStart by rememberUpdatedState { onStart.invoke() }
    val currentOnResume by rememberUpdatedState { onResume.invoke() }
    val currentOnPause by rememberUpdatedState { onPause.invoke() }
    val currentOnStop by rememberUpdatedState { onStop.invoke() }
    val currentOnDestroy by rememberUpdatedState { onDestroy.invoke() }
    val currentOnAny by rememberUpdatedState { onAny.invoke() }
    val currentOnDispose by rememberUpdatedState { onDispose.invoke() }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            val state = when (event) {
                Lifecycle.Event.ON_CREATE -> "ON_CREATE"
                Lifecycle.Event.ON_START -> "ON_START"
                Lifecycle.Event.ON_RESUME -> "ON_RESUME"
                Lifecycle.Event.ON_PAUSE -> "ON_PAUSE"
                Lifecycle.Event.ON_STOP -> "ON_STOP"
                Lifecycle.Event.ON_DESTROY -> "ON_DESTROY"
                Lifecycle.Event.ON_ANY -> "ON_ANY"
            }

            Log.w("LifecycleListener", "LifecycleListener: $state")

            when (event) {
                Lifecycle.Event.ON_CREATE -> currentOnCreate()
                Lifecycle.Event.ON_START -> currentOnStart()
                Lifecycle.Event.ON_RESUME -> currentOnResume()
                Lifecycle.Event.ON_PAUSE -> currentOnPause()
                Lifecycle.Event.ON_STOP -> currentOnStop()
                Lifecycle.Event.ON_DESTROY -> currentOnDestroy()
                Lifecycle.Event.ON_ANY -> currentOnAny()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            currentOnDispose()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}