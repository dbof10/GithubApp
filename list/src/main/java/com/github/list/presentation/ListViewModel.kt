package com.github.list.presentation

import com.github.common.arch.FlowReduxViewModel
import com.github.list.presentation.redux.ListResultsAction
import com.github.list.presentation.redux.ListResultsState
import com.github.list.presentation.redux.ListStateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val machine: ListStateMachine
) : FlowReduxViewModel<ListResultsState, ListResultsAction, ListResultsNavigation>(machine) {

    private lateinit var navigator: ListNavigator

    override fun handleNavigation(navigation: ListResultsNavigation) {
        if (navigation is ListResultsNavigation.OpenDetail) {
            navigator.navigateToDetail(navigation.username)
        }
    }

    fun setNavigator(navigator: ListNavigator) {
        this.navigator = navigator
    }

}
