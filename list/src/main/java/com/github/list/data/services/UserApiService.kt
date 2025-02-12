package com.github.list.data.services

import com.github.list.data.remote.response.GitHubUser
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {

    @GET("users")
    suspend fun getUsers(
        @Query("per_page") limit: Int,
        @Query("since") since: Int
    ): List<GitHubUser>
}
