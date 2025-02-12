package com.github.list.data.di

import com.github.common.utils.TimeUtils
import com.github.list.data.local.dao.UsersDao
import com.github.list.data.repositories.LastFetchPreferences
import com.github.list.data.repositories.UserRepositoryImpl
import com.github.list.data.services.UserApiService
import com.github.list.domain.repositories.UserRepository
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
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }


    @Provides
    fun provideUserRepository(
        lastFetchPreferences: LastFetchPreferences,
        apiService: UserApiService,
        dao: UsersDao,
        timeUtils: TimeUtils
    ): UserRepository = UserRepositoryImpl(lastFetchPreferences, apiService, dao, timeUtils)
}
