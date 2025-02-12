package com.github.list.data

import com.github.common.utils.TimeUtils
import com.github.list.data.local.dao.UsersDao
import com.github.list.data.local.entity.LocalGithubUser
import com.github.list.data.local.entity.toLocalEntity
import com.github.list.data.remote.response.GitHubUser
import com.github.list.data.repositories.LastFetchPreferences
import com.github.list.data.repositories.UserRepositoryImpl
import com.github.list.data.services.UserApiService
import com.github.test.MockkUnitTest
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserRepositoryImplTest : MockkUnitTest() {

    @RelaxedMockK
    lateinit var lastFetchPreferences: LastFetchPreferences

    @RelaxedMockK
    lateinit var remoteService: UserApiService

    @RelaxedMockK
    lateinit var localService: UsersDao

    @RelaxedMockK
    lateinit var timeUtils: TimeUtils

    private lateinit var repository: UserRepositoryImpl

    override fun setUp() {
        super.setUp()
        repository =
            UserRepositoryImpl(lastFetchPreferences, remoteService, localService, timeUtils)
    }

    @Test
    fun `when first page and cache is empty, should fetch from remote, store in local and update lastFetchTime`() =
        runTest {
            // Given
            coEvery { localService.getUsers() } returns emptyList()
            val remoteUsers = getMockUsers()
            coEvery { remoteService.getUsers(1, 0) } returns remoteUsers
            coEvery { localService.insert(remoteUsers.map { it.toLocalEntity() }) } just Runs
            every { timeUtils.currentMillis() } returns 1700000000000L

            // When
            val result = repository.getUsers(page = 1, since = 0)

            // Then
            assertEquals(remoteUsers.map { it.toLocalEntity() }, result)
            coVerify { remoteService.getUsers(1, 0) }
            coVerify { localService.insert(remoteUsers.map { it.toLocalEntity() }) }
            coVerify { lastFetchPreferences.saveLastFetchTime(1700000000000L) }
        }

    private fun getMockUsers(): List<GitHubUser> {
        return listOf(
            GitHubUser(
                login = "eos",
                id = 7799,
                nodeId = "morbi",
                avatarUrl = "https://duckduckgo.com/?q=eripuit",
                gravatarId = "consetetur",
                url = "https://search.yahoo.com/search?p=voluptaria",
                htmlUrl = "https://www.google.com/#q=mauris",
                followersUrl = "https://search.yahoo.com/search?p=quo",
                followingUrl = "http://www.bing.com/search?q=causae",
                gistsUrl = "https://www.google.com/#q=scripta",
                starredUrl = "https://www.google.com/#q=fermentum",
                subscriptionsUrl = "https://search.yahoo.com/search?p=noluisse",
                organizationsUrl = "https://www.google.com/#q=quaestio",
                reposUrl = "https://www.google.com/#q=duis",
                eventsUrl = "https://search.yahoo.com/search?p=consul",
                receivedEventsUrl = "https://duckduckgo.com/?q=a",
                type = "pellentesque",
                userViewType = "eleifend",
                siteAdmin = false
            ),
            GitHubUser(
                login = "primis",
                id = 9103,
                nodeId = "falli",
                avatarUrl = "https://www.google.com/#q=duis",
                gravatarId = "adipisci",
                url = "https://www.google.com/#q=ligula",
                htmlUrl = "http://www.bing.com/search?q=interdum",
                followersUrl = "https://www.google.com/#q=auctor",
                followingUrl = "https://duckduckgo.com/?q=error",
                gistsUrl = "https://search.yahoo.com/search?p=voluptatibus",
                starredUrl = "https://www.google.com/#q=at",
                subscriptionsUrl = "https://www.google.com/#q=adipisci",
                organizationsUrl = "https://duckduckgo.com/?q=eruditi",
                reposUrl = "https://www.google.com/#q=nascetur",
                eventsUrl = "https://search.yahoo.com/search?p=utamur",
                receivedEventsUrl = "https://duckduckgo.com/?q=veri",
                type = "facilis",
                userViewType = "sonet",
                siteAdmin = false
            )
        )
    }

    @Test
    fun `when first page and cache is valid, should return local data without API call`() =
        runTest {
            // Given
            val localUsers = listOf(
                LocalGithubUser(
                    login = "dicat",
                    id = 7412,
                    nodeId = "mi",
                    avatarUrl = "https://search.yahoo.com/search?p=gloriatur",
                    gravatarId = "maximus",
                    url = "https://search.yahoo.com/search?p=platea",
                    htmlUrl = "https://www.google.com/#q=vocent",
                    followersUrl = "http://www.bing.com/search?q=integer",
                    followingUrl = "https://search.yahoo.com/search?p=montes",
                    gistsUrl = "https://duckduckgo.com/?q=vitae",
                    starredUrl = "https://search.yahoo.com/search?p=nihil",
                    subscriptionsUrl = "https://duckduckgo.com/?q=reformidans",
                    organizationsUrl = "https://www.google.com/#q=qualisque",
                    reposUrl = "https://search.yahoo.com/search?p=oporteat",
                    eventsUrl = "https://www.google.com/#q=ludus",
                    receivedEventsUrl = "https://search.yahoo.com/search?p=novum",
                    type = "quaeque",
                    userViewType = "feugiat",
                    siteAdmin = false
                ),
                LocalGithubUser(
                    login = "propriae",
                    id = 8505,
                    nodeId = "ferri",
                    avatarUrl = "http://www.bing.com/search?q=delectus",
                    gravatarId = "mus",
                    url = "http://www.bing.com/search?q=laoreet",
                    htmlUrl = "https://duckduckgo.com/?q=iudicabit",
                    followersUrl = "http://www.bing.com/search?q=oratio",
                    followingUrl = "http://www.bing.com/search?q=porta",
                    gistsUrl = "https://search.yahoo.com/search?p=fabulas",
                    starredUrl = "https://search.yahoo.com/search?p=indoctum",
                    subscriptionsUrl = "http://www.bing.com/search?q=quot",
                    organizationsUrl = "http://www.bing.com/search?q=velit",
                    reposUrl = "http://www.bing.com/search?q=sit",
                    eventsUrl = "https://search.yahoo.com/search?p=tempor",
                    receivedEventsUrl = "https://search.yahoo.com/search?p=appetere",
                    type = "ea",
                    userViewType = "interdum",
                    siteAdmin = false
                )
            )
            coEvery { localService.getUsers() } returns localUsers
            every { lastFetchPreferences.getLastFetchTime() } returns System.currentTimeMillis()
            every { timeUtils.currentMillis() } returns System.currentTimeMillis()

            // When
            val result = repository.getUsers(page = 1, since = 0)

            // Then
            assertEquals(localUsers, result)
            coVerify(exactly = 0) { remoteService.getUsers(any(), any()) }
            coVerify(exactly = 0) { lastFetchPreferences.saveLastFetchTime(any()) }
        }

    @Test
    fun `when first page and cache is expired, should fetch new data from remote and update lastFetchTime`() =
        runTest {
            // Given
            val localUsers = listOf(
                LocalGithubUser(
                    login = "scripserit",
                    id = 2286,
                    nodeId = "amet",
                    avatarUrl = "https://search.yahoo.com/search?p=conclusionemque",
                    gravatarId = "meliore",
                    url = "http://www.bing.com/search?q=antiopam",
                    htmlUrl = "https://duckduckgo.com/?q=laudem",
                    followersUrl = "https://search.yahoo.com/search?p=eirmod",
                    followingUrl = "https://www.google.com/#q=detraxit",
                    gistsUrl = "https://duckduckgo.com/?q=nullam",
                    starredUrl = "https://duckduckgo.com/?q=definiebas",
                    subscriptionsUrl = "https://duckduckgo.com/?q=conclusionemque",
                    organizationsUrl = "https://search.yahoo.com/search?p=pharetra",
                    reposUrl = "https://duckduckgo.com/?q=sapientem",
                    eventsUrl = "http://www.bing.com/search?q=convallis",
                    receivedEventsUrl = "https://www.google.com/#q=repudiandae",
                    type = "vel",
                    userViewType = "laudem",
                    siteAdmin = false
                ),
                LocalGithubUser(
                    login = "auctor",
                    id = 5340,
                    nodeId = "solum",
                    avatarUrl = "https://duckduckgo.com/?q=eleifend",
                    gravatarId = "fringilla",
                    url = "https://www.google.com/#q=feugiat",
                    htmlUrl = "http://www.bing.com/search?q=fames",
                    followersUrl = "https://search.yahoo.com/search?p=senectus",
                    followingUrl = "http://www.bing.com/search?q=est",
                    gistsUrl = "https://duckduckgo.com/?q=homero",
                    starredUrl = "http://www.bing.com/search?q=lorem",
                    subscriptionsUrl = "http://www.bing.com/search?q=honestatis",
                    organizationsUrl = "https://www.google.com/#q=efficitur",
                    reposUrl = "https://www.google.com/#q=dolor",
                    eventsUrl = "http://www.bing.com/search?q=vidisse",
                    receivedEventsUrl = "https://www.google.com/#q=petentium",
                    type = "penatibus",
                    userViewType = "porro",
                    siteAdmin = false
                )
            )
            val remoteUsers = getMockUsers()

            coEvery { localService.getUsers() } returns localUsers
            every { lastFetchPreferences.getLastFetchTime() } returns 1600000000000L
            every { timeUtils.currentMillis() } returns 1700000000000L
            coEvery { remoteService.getUsers(1, 0) } returns remoteUsers
            coEvery { localService.insert(remoteUsers.map { it.toLocalEntity() }) } just Runs

            // When
            val result = repository.getUsers(page = 1, since = 0)

            // Then
            assertEquals(remoteUsers.map { it.toLocalEntity() }, result)
            coVerify { remoteService.getUsers(1, 0) }
            coVerify { localService.insert(remoteUsers.map { it.toLocalEntity() }) }
            coVerify { lastFetchPreferences.saveLastFetchTime(1700000000000L) }
        }

    @Test
    fun `when requesting second page, should always fetch from remote`() = runTest {
        // Given
        val remoteUsers = getMockUsers()
        coEvery { remoteService.getUsers(2, 1) } returns remoteUsers

        val result = repository.getUsers(page = 2, since = 1)

        // Then
        assertEquals(remoteUsers.map { it.toLocalEntity() }, result)
        coVerify { remoteService.getUsers(2, 1) }
        coVerify(exactly = 0) { localService.getUsers() }
        coVerify(exactly = 0) { lastFetchPreferences.saveLastFetchTime(any()) }
    }
}
