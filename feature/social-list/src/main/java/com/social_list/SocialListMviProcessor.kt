package com.social_list

import com.mvi.MviProcessor

class SocialListViewModel : MviProcessor<SocialListState, SocialListEvent, SocialListSingleEvent>() {

    override fun initialState(): SocialListState {
        return SocialListState()
    }

    override fun reduce(event: SocialListEvent, state: SocialListState): SocialListState {
        return state
    }

    override suspend fun handleEvent(event: SocialListEvent, state: SocialListState): SocialListEvent? {
        return null
    }
}