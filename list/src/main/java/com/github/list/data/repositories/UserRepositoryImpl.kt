package com.github.list.data.repositories

import com.github.common.utils.TimeUtils
import com.github.list.data.local.dao.UsersDao
import com.github.list.data.local.entity.LocalGithubUser
import com.github.list.data.local.entity.toLocalEntity
import com.github.list.data.services.UserApiService
import com.github.list.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val lastFetchPreferences: LastFetchPreferences,
    private val remoteService: UserApiService,
    private val localService: UsersDao,
    private val timeUtils: TimeUtils
) : UserRepository {

    companion object {
        const val DURATION_ONE_HOUR_IN_MILLIS = 60 * 60 * 1000
    }

    /**
     *  GitHub API pagination issue:
     *  When using `since=80`, the API returns a user with `id=101`.
     *  However, querying with `since=100` also returns the same user `id=101`.
     *  This overlap in results causes conflicts when inserting into the local database,
     *  leading to data inconsistency and crashes in `LazyColumn`.
     *
     *  Due to this issue, caching is only reliable for the first page (`since=0`),
     *  as it guarantees a fresh and consistent dataset.
     *  Further caching is not feasible because the `since` parameter does not provide
     *  a strict guarantee of unique or incremental user IDs.
     */

    override suspend fun getUsers(page: Int, since: Int): List<LocalGithubUser> {

        if (since == 0) { //only deal with cache with since = 0 it means first page

            val local = localService.getUsers()
            if (local.isEmpty()) {
                val remote = remoteService.getUsers(page, since)
                    .map {
                        it.toLocalEntity()
                    }
                lastFetchPreferences.saveLastFetchTime(timeUtils.currentMillis())
                localService.insert(remote)
                return remote
            } else {
                if (cacheExpire()) {
                    val remote = remoteService.getUsers(page, since)
                        .map {
                            it.toLocalEntity()
                        }
                    lastFetchPreferences.saveLastFetchTime(timeUtils.currentMillis())
                    localService.insert(remote)
                    return remote
                } else {
                    return local
                }
            }
        }

        val remote = remoteService.getUsers(page, since)
            .map {
                it.toLocalEntity()
            }
        return remote

    }

    private fun cacheExpire(): Boolean {
        val lastFetch = lastFetchPreferences.getLastFetchTime()
        val current = timeUtils.currentMillis()
        return (current - lastFetch) >= DURATION_ONE_HOUR_IN_MILLIS
    }
}
