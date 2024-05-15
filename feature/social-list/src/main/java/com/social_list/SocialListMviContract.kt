package com.social_list

import com.mvi.ScreenEvent
import com.mvi.ScreenSingleEvent
import com.mvi.ScreenState

class SocialListState : ScreenState

sealed interface SocialListEvent : ScreenEvent

sealed interface SocialListSingleEvent : ScreenSingleEvent