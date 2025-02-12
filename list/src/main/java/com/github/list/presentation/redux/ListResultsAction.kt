package com.github.list.presentation.redux

import com.github.list.presentation.model.UserUiModel


sealed class ListResultsAction {

    data object StartLoadResult : ListResultsAction()

    data object UsersResultLoading : ListResultsAction()

    data class UsersResultLoaded(val results: List<UserUiModel>) : ListResultsAction()

    data class UsersResultError(val error: Throwable) : ListResultsAction()

    data class UserDetailClicked(val model: UserUiModel) : ListResultsAction()


    data object LoadMore : ListResultsAction()

    data class UsersMoreLoaded(val results: List<UserUiModel>) : ListResultsAction()

}
