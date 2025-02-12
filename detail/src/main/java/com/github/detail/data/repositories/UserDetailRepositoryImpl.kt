package com.github.detail.data.repositories

import com.github.detail.data.response.UserDetail
import com.github.detail.data.services.UserDetailApiService
import com.github.detail.domain.repositories.UserDetailRepository


class UserDetailRepositoryImpl(
    private val remoteService: UserDetailApiService,
) : UserDetailRepository {

    override suspend fun getUser(id: String): UserDetail {
        return remoteService.getUser(id)
    }
}
