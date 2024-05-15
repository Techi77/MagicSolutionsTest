package com.core.ui.modifier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role

fun Modifier.clickableOff() = clickableRippleOff {}

fun Modifier.clickableRippleOff(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    this then Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = onClick,
        role = role,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    )
}

fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    ripple: Boolean = true,
    key: Any? = Unit,
    onClick: () -> Unit,
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }

    val onMultipleClick = remember(key1 = key) {
        { multipleEventsCutter.processEvent(event = onClick) }
    }

    this then Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = onMultipleClick,
        role = role,
        indication = if (ripple) rememberRipple() else null,
        interactionSource = remember { MutableInteractionSource() }
    )
}

internal class MultipleEventsCutter private constructor() {
    companion object {
        private val instance = MultipleEventsCutter()
        fun get(): MultipleEventsCutter = instance
    }

    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    private val cuttingTimeMs: Long = 400L

    fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= cuttingTimeMs) {
            event.invoke()
        }

        lastEventTimeMs = now
    }

    fun processEvent(): Boolean {
        var result = true

        if (now - lastEventTimeMs >= cuttingTimeMs) {
            result = false
        }

        lastEventTimeMs = now
        return result
    }
}