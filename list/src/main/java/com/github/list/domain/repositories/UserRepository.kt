package com.github.list.domain.repositories

import com.github.list.data.local.entity.LocalGithubUser

interface UserRepository {

    suspend fun getUsers(page: Int, since: Int): List<LocalGithubUser>

}
