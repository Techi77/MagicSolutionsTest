package com.core.ui.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

fun Context.getActivity(): ComponentActivity? {
    var context = this

    while (context is ContextWrapper) {
        if (context is ComponentActivity) return context
        context = context.baseContext
    }

    return null
}

@Composable
fun getActivity(): ComponentActivity? {
    return LocalContext.current.getActivity()
}