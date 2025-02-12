package com.github.detail.data.di

import com.github.detail.data.repositories.UserDetailRepositoryImpl
import com.github.detail.data.services.UserDetailApiService
import com.github.detail.domain.repositories.UserDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsersRepositoryModule {


    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserDetailApiService {
        return retrofit.create(UserDetailApiService::class.java)
    }


    @Provides
    fun provideUserRepository(
        apiService: UserDetailApiService,
    ): UserDetailRepository = UserDetailRepositoryImpl(apiService)
}
