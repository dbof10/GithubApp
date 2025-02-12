package com.github.common.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.arch.FlowReduxStateMachine
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
abstract class FlowReduxViewModel<State : Any, Action : Any, Navigation : Any>(
    private val stateMachine: FlowReduxStateMachine<State, Action, Navigation>,
) : ViewModel() {

    private val _stateflow = MutableStateFlow(stateMachine.initialState)
    val stateflow = _stateflow.asStateFlow()

    private var currentNavigation: Navigation? = null

    init {
        stateMachine.initStore()
        viewModelScope.launch {
            launch {
                stateMachine.state.collect {
                    _stateflow.value = it
                }
            }
            launch {
                stateMachine.navigation.collect {
                    currentNavigation = it
                    handleNavigation(it)
                }
            }
        }
    }

    abstract fun handleNavigation(navigation: Navigation)

    fun currentNavigation() = currentNavigation

    fun dispatch(action: Action) {
        viewModelScope.launch {
            stateMachine.dispatch(action = action)
        }
    }

    override fun onCleared() {
        super.onCleared()
        // handle dispose stateMachine
        stateMachine.dispose()
    }
}
