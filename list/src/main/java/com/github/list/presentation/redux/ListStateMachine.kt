@file:OptIn(FlowPreview::class)

package com.github.list.presentation.redux

import androidx.annotation.VisibleForTesting
import com.github.arch.FlowReduxStateMachine
import com.github.arch.Reducer
import com.github.arch.SideEffect
import com.github.arch.ofType
import com.github.common.error.EmptyListException
import com.github.common.utils.DispatchersProvider
import com.github.list.domain.usecase.GetUsersUseCase
import com.github.list.presentation.ListResultsNavigation
import com.github.list.presentation.model.toUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ListStateMachine @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val getUsersUseCase: GetUsersUseCase
) : FlowReduxStateMachine<ListResultsState, ListResultsAction, ListResultsNavigation>() {

    companion object {
        private const val LIMIT = 20
    }

    override val initialState: ListResultsState
        get() = ListResultsState.InitState

    override fun sideEffects(): List<SideEffect<ListResultsState, ListResultsAction>> {
        return listOf(
            navigationSideEffect, handleStartLoadResultSideEffect,
            handleLoadMoreResultSideEffect
        )
    }

    override fun reducer(): Reducer<ListResultsState, ListResultsAction> = this::reducerImpl

    @VisibleForTesting
    fun reducerImpl(state: ListResultsState, action: ListResultsAction): ListResultsState {
        return when (action) {
            is ListResultsAction.UsersResultLoading -> ListResultsState.UsersResultLoading(state)

            is ListResultsAction.UsersResultLoaded -> {
                val items = action.results
                if (items.isEmpty()) {
                    ListResultsState.UsersResultError(state, EmptyListException)
                } else {
                    ListResultsState.UsersResultLoaded(state, items, since = LIMIT)
                }
            }

            is ListResultsAction.UsersMoreLoaded -> {
                val items = action.results
                val newList = (state.users + items).distinctBy { it.id } //github api returns duplicate item from a specific page and next page. Explain details in UserRepositoryImpl.kt
                ListResultsState.UsersResultLoaded(state, newList, since = LIMIT + state.since)
            }

            is ListResultsAction.UsersResultError -> {
                ListResultsState.UsersResultError(state, action.error)
            }

            else -> state
        }
    }

    @VisibleForTesting
    val handleStartLoadResultSideEffect: SideEffect<ListResultsState, ListResultsAction> =
        { actions, getState ->
            actions.ofType(ListResultsAction.StartLoadResult::class)
                .filter { getState() is ListResultsState.InitState } // only handle for the init state, it will avoid reloading when back from detail
                .flatMapLatest {
                    return@flatMapLatest flow {
                        emit(ListResultsAction.UsersResultLoading)
                        try {
                            val users = getUsersUseCase.invoke(LIMIT, 0)
                                .map {
                                    it.toUiModel()
                                }
                            emit(ListResultsAction.UsersResultLoaded(users))
                        } catch (e: Exception) {
                            emit(ListResultsAction.UsersResultError(e))
                        }
                    }
                }
                .flowOn(dispatchersProvider.io)
        }

    @VisibleForTesting
    val navigationSideEffect =
        createNavigationSideEffect<ListResultsAction> { currentState, action ->
            when (action) {
                is ListResultsAction.UserDetailClicked -> {
                    ListResultsNavigation.OpenDetail(action.model.login)
                }

                else -> null
            }
        }

    @VisibleForTesting
    val handleLoadMoreResultSideEffect: SideEffect<ListResultsState, ListResultsAction> =
        { actions, getState ->
            actions.ofType(ListResultsAction.LoadMore::class)
                .flatMapLatest {
                    try {
                        val users = getUsersUseCase.invoke(LIMIT, getState().since)
                            .map {
                                it.toUiModel()
                            }
                        return@flatMapLatest flowOf(ListResultsAction.UsersMoreLoaded(users))
                    } catch (e: Exception) {
                        return@flatMapLatest emptyFlow() //muted when error on loadmore, just UI behavior
                    }

                }
                .flowOn(dispatchersProvider.io)
        }
}
