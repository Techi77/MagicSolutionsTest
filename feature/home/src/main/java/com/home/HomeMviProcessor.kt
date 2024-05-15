package com.home

import com.mvi.MviProcessor

class HomeViewModel : MviProcessor<HomeState, HomeEvent, HomeSingleEvent>() {

    override fun initialState(): HomeState {
        return HomeState()
    }

    override fun reduce(event: HomeEvent, state: HomeState): HomeState {
        return state
    }

    override suspend fun handleEvent(event: HomeEvent, state: HomeState): HomeEvent? {
        return null
    }
}