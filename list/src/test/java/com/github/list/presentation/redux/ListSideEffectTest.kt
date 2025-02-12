package com.github.list.presentation.redux;

import app.cash.turbine.test
import com.github.arch.getState
import com.github.common.utils.DispatchersProvider
import com.github.list.data.local.entity.LocalGithubUser
import com.github.list.domain.usecase.GetUsersUseCase
import com.github.list.presentation.ListResultsNavigation
import com.github.list.presentation.model.UserUiModel
import com.github.list.presentation.model.toUiModel
import com.github.test.MockkUnitTest
import com.github.test.TestDispatcherProvider
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ListSideEffectTest : MockkUnitTest() {

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
    fun startLoadSuccess() = runTest {
        val user = LocalGithubUser(
            login = "habitasse",
            id = 4642,
            nodeId = "vim",
            avatarUrl = "http://www.bing.com/search?q=audire",
            gravatarId = "suavitate",
            url = "https://duckduckgo.com/?q=fermentum",
            htmlUrl = "http://www.bing.com/search?q=ex",
            followersUrl = "http://www.bing.com/search?q=conceptam",
            followingUrl = "https://search.yahoo.com/search?p=debet",
            gistsUrl = "http://www.bing.com/search?q=homero",
            starredUrl = "https://search.yahoo.com/search?p=finibus",
            subscriptionsUrl = "https://duckduckgo.com/?q=molestie",
            organizationsUrl = "https://search.yahoo.com/search?p=corrumpit",
            reposUrl = "https://www.google.com/#q=nonumy",
            eventsUrl = "https://search.yahoo.com/search?p=homero",
            receivedEventsUrl = "http://www.bing.com/search?q=ut",
            type = "netus",
            userViewType = "reprimique",
            siteAdmin = false
        )
        coEvery {
            getUsersUseCase.invoke(20, 0)
        } returns listOf(user)

        stateMachine.handleStartLoadResultSideEffect(
            flowOf(ListResultsAction.StartLoadResult),
            getState(defaultCurrentState),
        ).test {
            awaitItem().shouldBe(ListResultsAction.UsersResultLoading)
            awaitItem().shouldBeInstanceOf<ListResultsAction.UsersResultLoaded> {
                it.results.shouldBe(listOf(user.toUiModel()))
            }
            awaitComplete()
        }
    }

    @Test
    fun startLoadError() = runTest {

        val exception = RuntimeException()
        coEvery {
            getUsersUseCase.invoke(20, 0)
        } throws exception
        stateMachine.handleStartLoadResultSideEffect(
            flowOf(ListResultsAction.StartLoadResult),
            getState(defaultCurrentState),
        ).test {
            awaitItem().shouldBe(ListResultsAction.UsersResultLoading)
            awaitItem().shouldBeInstanceOf<ListResultsAction.UsersResultError> {
                it.error.shouldBe(exception)
            }
            awaitComplete()
        }
    }

    @Test
    fun loadMoreSuccess() = runTest {

        val user = LocalGithubUser(
            login = "habitasse",
            id = 4642,
            nodeId = "vim",
            avatarUrl = "http://www.bing.com/search?q=audire",
            gravatarId = "suavitate",
            url = "https://duckduckgo.com/?q=fermentum",
            htmlUrl = "http://www.bing.com/search?q=ex",
            followersUrl = "http://www.bing.com/search?q=conceptam",
            followingUrl = "https://search.yahoo.com/search?p=debet",
            gistsUrl = "http://www.bing.com/search?q=homero",
            starredUrl = "https://search.yahoo.com/search?p=finibus",
            subscriptionsUrl = "https://duckduckgo.com/?q=molestie",
            organizationsUrl = "https://search.yahoo.com/search?p=corrumpit",
            reposUrl = "https://www.google.com/#q=nonumy",
            eventsUrl = "https://search.yahoo.com/search?p=homero",
            receivedEventsUrl = "http://www.bing.com/search?q=ut",
            type = "netus",
            userViewType = "reprimique",
            siteAdmin = false
        )
        val user2 = LocalGithubUser(
            login = "choco",
            id = 1,
            nodeId = "vim",
            avatarUrl = "http://www.bing.com/search?q=audire",
            gravatarId = "suavitate",
            url = "",
            htmlUrl = "http://www.bing.com/search?q=ex",
            followersUrl = "http://www.bing.com/search?q=conceptam",
            followingUrl = "https://search.yahoo.com/search?p=debet",
            gistsUrl = "http://www.bing.com/search?q=homero",
            starredUrl = "https://search.yahoo.com/search?p=finibus",
            subscriptionsUrl = "https://duckduckgo.com/?q=molestie",
            organizationsUrl = "https://search.yahoo.com/search?p=corrumpit",
            reposUrl = "https://www.google.com/#q=nonumy",
            eventsUrl = "https://search.yahoo.com/search?p=homero",
            receivedEventsUrl = "http://www.bing.com/search?q=ut",
            type = "netus",
            userViewType = "reprimique",
            siteAdmin = false
        )

        coEvery {
            getUsersUseCase.invoke(20, 20)
        } returns listOf(user2)

        val loadedState =
            ListResultsState.UsersResultLoaded(defaultCurrentState, listOf(user.toUiModel()), 20)
        stateMachine.handleLoadMoreResultSideEffect(
            flowOf(ListResultsAction.LoadMore),
            getState(loadedState),
        ).test {
            awaitItem().shouldBeInstanceOf<ListResultsAction.UsersMoreLoaded> {
                it.results.shouldBe(listOf(user2.toUiModel()))
            }
            awaitComplete()
        }
    }

    @Test
    fun loadMoreError() = runTest {
        val user = LocalGithubUser(
            login = "habitasse",
            id = 4642,
            nodeId = "vim",
            avatarUrl = "http://www.bing.com/search?q=audire",
            gravatarId = "suavitate",
            url = "https://duckduckgo.com/?q=fermentum",
            htmlUrl = "http://www.bing.com/search?q=ex",
            followersUrl = "http://www.bing.com/search?q=conceptam",
            followingUrl = "https://search.yahoo.com/search?p=debet",
            gistsUrl = "http://www.bing.com/search?q=homero",
            starredUrl = "https://search.yahoo.com/search?p=finibus",
            subscriptionsUrl = "https://duckduckgo.com/?q=molestie",
            organizationsUrl = "https://search.yahoo.com/search?p=corrumpit",
            reposUrl = "https://www.google.com/#q=nonumy",
            eventsUrl = "https://search.yahoo.com/search?p=homero",
            receivedEventsUrl = "http://www.bing.com/search?q=ut",
            type = "netus",
            userViewType = "reprimique",
            siteAdmin = false
        )
        coEvery {
            getUsersUseCase.invoke(20, 20)
        } throws RuntimeException()

        val loadedState =
            ListResultsState.UsersResultLoaded(defaultCurrentState, listOf(user.toUiModel()), 20)
        stateMachine.handleLoadMoreResultSideEffect(
            flowOf(ListResultsAction.LoadMore),
            getState(loadedState),
        ).test {
            awaitComplete()
        }
    }


    @Test
    fun userClicked() = runTest {
        launch {
            stateMachine.navigation.test {
                awaitItem().shouldBeInstanceOf<ListResultsNavigation.OpenDetail> {
                    it.username.shouldBe("invidunt")
                }
                cancelAndIgnoreRemainingEvents()
            }
        }

        launch {
            stateMachine.navigationSideEffect(
                flowOf(ListResultsAction.UserDetailClicked(
                    UserUiModel(
                        login = "invidunt",
                        avatarUrl = "https://www.google.com/#q=posidonium",
                        id = 7197,
                        url = "https://duckduckgo.com/?q=interesset",
                        htmlUrl = "https://duckduckgo.com/?q=aeque",
                        followersUrl = "https://duckduckgo.com/?q=a",
                        followingUrl = "https://search.yahoo.com/search?p=mei",
                        gistsUrl = "https://search.yahoo.com/search?p=hendrerit",
                        starredUrl = "https://search.yahoo.com/search?p=oporteat",
                        reposUrl = "http://www.bing.com/search?q=natoque"
                    )
                )),
                getState(defaultCurrentState),
            ).test {
                awaitComplete()
            }
        }
    }
}
