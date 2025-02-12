package com.github.list.domain.usecase

import com.github.list.data.local.entity.LocalGithubUser
import com.github.list.domain.repositories.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: UserRepository) {

    suspend fun invoke(page: Int, since: Int): List<LocalGithubUser> {
        return repository.getUsers(page, since)
    }
}
