package com.github.detail.data

import com.github.detail.data.repositories.UserDetailRepositoryImpl
import com.github.detail.data.response.UserDetail
import com.github.detail.data.services.UserDetailApiService
import com.github.test.MockkUnitTest
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDetailRepositoryImplTest : MockkUnitTest() {

    @RelaxedMockK
    lateinit var remoteService: UserDetailApiService

    private lateinit var repository: UserDetailRepositoryImpl

    override fun setUp() {
        super.setUp()
        repository = UserDetailRepositoryImpl(remoteService)
    }

    @Test
    fun `when getUser is called, it should return user details`() = runTest {
        // Given
        val userId = "123"
        val expectedUserDetail = UserDetail(
            login = "veritus",
            id = 7827,
            nodeId = "erroribus",
            avatarUrl = "http://www.bing.com/search?q=condimentum",
            gravatarId = "veritus",
            url = "http://www.bing.com/search?q=solum",
            htmlUrl = "https://search.yahoo.com/search?p=non",
            followersUrl = "https://search.yahoo.com/search?p=ignota",
            followingUrl = "https://duckduckgo.com/?q=leo",
            gistsUrl = "https://search.yahoo.com/search?p=mi",
            starredUrl = "http://www.bing.com/search?q=no",
            subscriptionsUrl = "https://search.yahoo.com/search?p=ocurreret",
            organizationsUrl = "https://search.yahoo.com/search?p=postea",
            reposUrl = "https://search.yahoo.com/search?p=brute",
            eventsUrl = "http://www.bing.com/search?q=accumsan",
            receivedEventsUrl = "https://www.google.com/#q=fames",
            type = "pertinax",
            userViewType = "causae",
            siteAdmin = false,
            name = null,
            company = null,
            blog = null,
            location = null,
            email = null,
            hireable = null,
            bio = null,
            twitterUsername = null,
            publicRepos = 6142,
            publicGists = 4853,
            followers = 6347,
            following = 7719,
            createdAt = "saperet",
            updatedAt = "netus"
        )
        coEvery { remoteService.getUser(userId) } returns expectedUserDetail

        val result = repository.getUser(userId)

        assertEquals(expectedUserDetail, result)
    }

}
