package com.github.list.presentation.redux


import com.github.common.utils.DispatchersProvider
import com.github.list.domain.usecase.GetUsersUseCase
import com.github.list.presentation.model.UserUiModel
import com.github.test.MockkUnitTest
import com.github.test.TestDispatcherProvider
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ListReducerTest : MockkUnitTest() {


    private val dispatchersProvider: DispatchersProvider = TestDispatcherProvider()

    @RelaxedMockK
    lateinit var getUsersUseCase: GetUsersUseCase

    private lateinit var stateMachine: ListStateMachine

    lateinit var defaultCurrentState: ListResultsState

    override fun setUp() {
        super.setUp()
        stateMachine = ListStateMachine(dispatchersProvider, getUsersUseCase)
        defaultCurrentState = ListResultsState.InitState
    }

    @Test
    fun loading() = runTest {
        val state =
            stateMachine.reducerImpl(defaultCurrentState, ListResultsAction.UsersResultLoading)
        state.shouldBeInstanceOf<ListResultsState.UsersResultLoading> {

        }
    }

    @Test
    fun loaded() = runTest {

        val users = listOf(
            UserUiModel(
                login = "vim",
                avatarUrl = "https://search.yahoo.com/search?p=atqui",
                id = 2821,
                url = "https://search.yahoo.com/search?p=est",
                htmlUrl = "http://www.bing.com/search?q=conceptam",
                followersUrl = "https://search.yahoo.com/search?p=debet",
                followingUrl = "https://duckduckgo.com/?q=volumus",
                gistsUrl = "https://duckduckgo.com/?q=porta",
                starredUrl = "https://search.yahoo.com/search?p=utamur",
                reposUrl = "https://search.yahoo.com/search?p=praesent"
            )
        )
        val state = stateMachine.reducerImpl(
            defaultCurrentState,
            ListResultsAction.UsersResultLoaded(users)
        )
        state.shouldBeInstanceOf<ListResultsState.UsersResultLoaded> {
            it.users.shouldBe(users)
            it.since.shouldBe(20)
        }
    }

    @Test
    fun error() = runTest {

        val error = RuntimeException()
        val state =
            stateMachine.reducerImpl(defaultCurrentState, ListResultsAction.UsersResultError(error))
        state.shouldBeInstanceOf<ListResultsState.UsersResultError> {
            it.error.shouldBe(error)
        }
    }

    @Test
    fun moreLoaded() = runTest {

        val users = listOf(
            UserUiModel(
                login = "vim",
                avatarUrl = "https://search.yahoo.com/search?p=atqui",
                id = 2821,
                url = "https://search.yahoo.com/search?p=est",
                htmlUrl = "http://www.bing.com/search?q=conceptam",
                followersUrl = "https://search.yahoo.com/search?p=debet",
                followingUrl = "https://duckduckgo.com/?q=volumus",
                gistsUrl = "https://duckduckgo.com/?q=porta",
                starredUrl = "https://search.yahoo.com/search?p=utamur",
                reposUrl = "https://search.yahoo.com/search?p=praesent"
            )
        )
        val users2 = listOf(
            UserUiModel(
                login = "vim2",
                avatarUrl = "https://search.yahoo.com/search?p=atqui",
                id = 1,
                url = "https://search.yahoo.com/search?p=est",
                htmlUrl = "http://www.bing.com/search?q=conceptam",
                followersUrl = "https://search.yahoo.com/search?p=debet",
                followingUrl = "https://duckduckgo.com/?q=volumus",
                gistsUrl = "https://duckduckgo.com/?q=porta",
                starredUrl = "https://search.yahoo.com/search?p=utamur",
                reposUrl = "https://search.yahoo.com/search?p=praesent"
            )
        )
        val prevState = ListResultsState.UsersResultLoaded(defaultCurrentState, users, 20)

        val state = stateMachine.reducerImpl(prevState, ListResultsAction.UsersMoreLoaded(users2))
        state.shouldBeInstanceOf<ListResultsState.UsersResultLoaded> {
            val newList = prevState.users + users2
            it.users.shouldBe(newList)
            it.since.shouldBe(40)
        }
    }
}
