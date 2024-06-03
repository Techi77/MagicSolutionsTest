package com.mvi

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class MviProcessor<S : ScreenState, E : ScreenEvent, SE : ScreenSingleEvent> : ViewModel() {

    @Suppress("PrivatePropertyName")
    private val TAG = this::class.java.simpleName

    protected abstract fun initialState(): S
    protected abstract fun reduce(event: E, state: S): S
    protected abstract suspend fun handleEvent(event: E, state: S): E?

    private val _initialState: S by lazy { initialState() }
    private val _uiState: MutableState<S> by lazy { mutableStateOf(_initialState) }
    val uiState: State<S> by lazy { _uiState }

    private val _event: MutableSharedFlow<E> = MutableSharedFlow()

    private val _singleEvent: MutableSharedFlow<SE> = MutableSharedFlow()
    val singleEvent: Flow<SE> = _singleEvent

    private val _tutorialStep = MutableStateFlow(0)
    val tutorialStep: StateFlow<Int> = _tutorialStep.asStateFlow()

    private val _tutorialItemsParameters = MutableStateFlow(Array<TutorialItemsParameters?>(size = 4){ _ -> null })
    val tutorialItemsParameters: StateFlow<Array<TutorialItemsParameters?>> = _tutorialItemsParameters.asStateFlow()
    init {
        Log.d(TAG, "init")
        subscribeToEvent()
    }

    fun sendEvent(event: E) {
        Log.d(TAG, "sendEvent: event = $event")

        viewModelScope.launch {
            _event.emit(value = event)
        }
    }

    fun setTutorialStep(step: Int) {
        println("setTutorialStep($step)")
        _tutorialStep.value = step
    }

    fun setTutorialItemsParameters(index: Int, offset: Pair<Float,Float>, size:Pair<Float,Float>, cornerRadius:Int) {
        _tutorialItemsParameters.value[index] = TutorialItemsParameters(offset, size, cornerRadius)
    }


    fun triggerSingleEvent(singleEvent: SE) {
        Log.d(TAG, "triggerSingleEvent: singleEvent = $singleEvent")

        viewModelScope.launch {
            _singleEvent.emit(singleEvent)
        }
    }

    private fun setState(state: S) {
        _uiState.value = state
    }

    private fun reduceInternal(event: E, state: S) {
        val newState = reduce(event, state)
        setState(newState)
    }

    private fun subscribeToEvent() {
        viewModelScope.launch {
            _event.collect { event ->
                Log.d(TAG, "subscribeToEvent: Collect event = $event")

                reduceInternal(event, _uiState.value)

                launch {
                    handleEvent(event, _uiState.value)?.let { newEvent -> sendEvent(newEvent) }
                }
            }
        }
    }
}
data class TutorialItemsParameters(val offset: Pair<Float,Float>, val size:Pair<Float,Float>, val cornerRadius:Int)
