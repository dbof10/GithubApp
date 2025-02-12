package com.github.detail.domain

import com.github.detail.data.response.UserDetail
import com.github.detail.domain.repositories.UserDetailRepository
import com.github.test.MockkUnitTest
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetUserDetailsUseCaseTest : MockkUnitTest() {

    @RelaxedMockK
    lateinit var repository: UserDetailRepository

    private lateinit var useCase: GetUserDetailsUseCase

   override fun setUp() {
        useCase = GetUserDetailsUseCase(repository)
    }

    @Test
    fun `when invoke is called, it should return user details`() = runTest {
        // Given
        val userId = "123"
        val expectedUserDetail = UserDetail(
            login = "erroribus",
            id = 5210,
            nodeId = "evertitur",
            avatarUrl = "https://duckduckgo.com/?q=alienum",
            gravatarId = "affert",
            url = "https://search.yahoo.com/search?p=vocibus",
            htmlUrl = "https://www.google.com/#q=dictas",
            followersUrl = "https://www.google.com/#q=mea",
            followingUrl = "http://www.bing.com/search?q=honestatis",
            gistsUrl = "http://www.bing.com/search?q=vestibulum",
            starredUrl = "https://duckduckgo.com/?q=gubergren",
            subscriptionsUrl = "http://www.bing.com/search?q=dicant",
            organizationsUrl = "https://search.yahoo.com/search?p=cubilia",
            reposUrl = "https://search.yahoo.com/search?p=class",
            eventsUrl = "http://www.bing.com/search?q=reque",
            receivedEventsUrl = "http://www.bing.com/search?q=sodales",
            type = "magna",
            userViewType = "pro",
            siteAdmin = false,
            name = null,
            company = null,
            blog = null,
            location = null,
            email = null,
            hireable = null,
            bio = null,
            twitterUsername = null,
            publicRepos = 7127,
            publicGists = 9408,
            followers = 3593,
            following = 9588,
            createdAt = "errem",
            updatedAt = "feugiat"
        )
        coEvery { repository.getUser(userId) } returns expectedUserDetail

        // When
        val result = useCase.invoke(userId)

        // Then
        assertEquals(expectedUserDetail, result)
    }

}
