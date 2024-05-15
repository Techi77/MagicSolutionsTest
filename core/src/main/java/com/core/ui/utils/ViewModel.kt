package com.core.ui.utils

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
inline fun <reified T : ViewModel> koinActivityViewModel(): T {
    return koinViewModel(
        viewModelStoreOwner = getActivity() as ComponentActivity
    )
}