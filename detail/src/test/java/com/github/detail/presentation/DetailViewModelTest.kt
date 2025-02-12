package com.github.detail.presentation

import androidx.lifecycle.SavedStateHandle
import com.github.common.utils.DispatchersProvider
import com.github.detail.data.response.UserDetail
import com.github.detail.domain.GetUserDetailsUseCase
import com.github.test.MockkUnitTest
import com.github.test.TestDispatcherProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DetailViewModelTest : MockkUnitTest() {

    @RelaxedMockK
    lateinit var getUserDetailsUseCase: GetUserDetailsUseCase

    @RelaxedMockK
    lateinit var savedStateHandle: SavedStateHandle

    private val dispatchersProvider: DispatchersProvider = TestDispatcherProvider()

    private lateinit var viewModel: DetailViewModel

    override fun setUp() {


        viewModel = DetailViewModel(savedStateHandle, getUserDetailsUseCase, dispatchersProvider)
    }

    @Test
    fun `when username is empty, should set error state`() = runTest {
        // Given
        every { savedStateHandle.get<String>(KEY_USER_NAME) } returns ""

        // When
        viewModel.load()

        // Then
        val state = viewModel.store.value
        assertTrue(state.error is RuntimeException)
        assertEquals("Cannot retrieve user id", state.error?.message)
    }

    @Test
    fun `when load is called, should fetch user details successfully`() = runTest {
        // Given
        val userName = "john_doe"
        every { savedStateHandle.get<String>(KEY_USER_NAME) } returns userName
        val expectedUser = UserDetail(
            login = "utinam",
            id = 6860,
            nodeId = "diam",
            avatarUrl = "http://www.bing.com/search?q=interdum",
            gravatarId = "volumus",
            url = "https://search.yahoo.com/search?p=vis",
            htmlUrl = "https://www.google.com/#q=ei",
            followersUrl = "https://duckduckgo.com/?q=mei",
            followingUrl = "https://search.yahoo.com/search?p=erroribus",
            gistsUrl = "https://search.yahoo.com/search?p=graeci",
            starredUrl = "https://www.google.com/#q=mus",
            subscriptionsUrl = "http://www.bing.com/search?q=viris",
            organizationsUrl = "https://duckduckgo.com/?q=aliquip",
            reposUrl = "https://www.google.com/#q=civibus",
            eventsUrl = "https://search.yahoo.com/search?p=adhuc",
            receivedEventsUrl = "https://www.google.com/#q=sed",
            type = "hinc",
            userViewType = "laoreet",
            siteAdmin = false,
            name = null,
            company = null,
            blog = null,
            location = null,
            email = null,
            hireable = null,
            bio = null,
            twitterUsername = null,
            publicRepos = 4512,
            publicGists = 4094,
            followers = 7395,
            following = 1344,
            createdAt = "euripidis",
            updatedAt = "equidem"
        )

        coEvery { getUserDetailsUseCase.invoke(userName) } returns expectedUser

        // When
        viewModel.load()

        // Then
        val state = viewModel.store.value
        assertEquals("Unknown", state.content?.name)
        assertEquals("7395", state.content?.follower)
        assertNull(state.error)
        assertFalse(state.isLoading)
    }

    @Test
    fun `when load fails, should set error state`() = runTest {
        // Given
        val userName = "john_doe"
        every { savedStateHandle.get<String>(KEY_USER_NAME) } returns userName
        coEvery { getUserDetailsUseCase.invoke(userName) } throws RuntimeException("Network error")

        // When
        viewModel.load()

        // Then
        val state = viewModel.store.value
        assertTrue(state.error is RuntimeException)
        assertEquals("Network error", state.error?.message)
        assertFalse(state.isLoading)
    }
}
