package com.github.detail.domain.repositories

import com.github.detail.data.response.UserDetail


interface UserDetailRepository {

    suspend fun getUser(id: String): UserDetail

}
