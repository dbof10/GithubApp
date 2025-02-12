package com.github.list.domain

import com.github.list.data.local.entity.LocalGithubUser
import com.github.list.domain.repositories.UserRepository
import com.github.list.domain.usecase.GetUsersUseCase
import com.github.test.MockkUnitTest
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetUsersUseCaseTest : MockkUnitTest() {

    @RelaxedMockK
    lateinit var repository: UserRepository

    private lateinit var useCase: GetUsersUseCase

   override fun setUp() {
        useCase = GetUsersUseCase(repository)
    }

    @Test
    fun `when invoke is called, it should return users list`() = runTest {
        val expectedUsers = listOf(LocalGithubUser(
            login = "scripta",
            id = 2196,
            nodeId = "movet",
            avatarUrl = "https://search.yahoo.com/search?p=ad",
            gravatarId = "dicat",
            url = "http://www.bing.com/search?q=vitae",
            htmlUrl = "https://www.google.com/#q=commune",
            followersUrl = "https://www.google.com/#q=convallis",
            followingUrl = "https://www.google.com/#q=legimus",
            gistsUrl = "https://www.google.com/#q=inimicus",
            starredUrl = "https://www.google.com/#q=convenire",
            subscriptionsUrl = "https://www.google.com/#q=causae",
            organizationsUrl = "https://www.google.com/#q=ludus",
            reposUrl = "https://www.google.com/#q=deserunt",
            eventsUrl = "https://search.yahoo.com/search?p=amet",
            receivedEventsUrl = "https://www.google.com/#q=eruditi",
            type = "aperiri",
            userViewType = "integer",
            siteAdmin = false
        ))
        coEvery { repository.getUsers(1,1) } returns expectedUsers

        // When
        val result = useCase.invoke(1,1)

        // Then
        assertEquals(expectedUsers, result)
    }

}
