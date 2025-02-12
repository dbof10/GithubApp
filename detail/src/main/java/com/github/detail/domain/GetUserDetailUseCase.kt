package com.github.detail.domain

import com.github.detail.data.response.UserDetail
import com.github.detail.domain.repositories.UserDetailRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(private val repository: UserDetailRepository) {

    suspend fun invoke(id: String): UserDetail {
        return repository.getUser(id)
    }
}
