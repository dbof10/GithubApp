package com.github.list.presentation.redux

import com.github.list.presentation.model.UserUiModel


sealed class ListResultsState(currentState: ListResultsState?) {

    open val since: Int = currentState?.since ?: 0

    open val isLoading: Boolean = currentState?.isLoading ?: false

    open val users: List<UserUiModel> = currentState?.users ?: emptyList()

    open val error: Throwable? = currentState?.error

    data object InitState : ListResultsState(null)

    class UsersResultLoading(currentState: ListResultsState) : ListResultsState(currentState) {
        override val isLoading = true
        override val users: List<UserUiModel> = emptyList()
        override val error: Throwable? = null
    }

    class UsersResultLoaded(currentState: ListResultsState, override val users: List<UserUiModel>,
                            override val since: Int) :
        ListResultsState(currentState) {
        override val isLoading = false
        override val error: Throwable? = null // clear all error while result loaded

    }

    class UsersResultError(currentState: ListResultsState, override val error: Throwable) :
        ListResultsState(currentState) {
        override val isLoading = false
        override val users: List<UserUiModel> = emptyList()

    }

}
