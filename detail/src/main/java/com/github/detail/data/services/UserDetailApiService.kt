package com.github.detail.data.services

import com.github.detail.data.response.UserDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface UserDetailApiService {

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: String,
    ): UserDetail
}
