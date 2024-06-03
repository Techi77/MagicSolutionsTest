package com.social_list

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.core.R
import com.core.ui.composable.LifecycleListener
import com.core.ui.utils.getActivity
import com.mvi.main_mvi.MainEvent
import com.mvi.main_mvi.MainViewModel
import com.social_list.composable.ScreenV3V4

@Composable
internal fun SocialListScreen(
    viewModel: SocialListViewModel,
    mainViewModel: MainViewModel,
) {
    val activity = getActivity()
    val context = LocalContext.current

    var onBackCount by remember { mutableIntStateOf(0) }

    ScreenV3V4 ({ viewModel.sendEvent(it) }, viewModel )

    LifecycleListener(
        onStart = { onBackCount = 0 },
    )

    BackHandler(enabled = onBackCount < 1) {
        mainViewModel.sendEvent(
            MainEvent.RateUp(activity = activity) {
                ++onBackCount
                Toast.makeText(context, context.getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show()
            }
        )
    }
}