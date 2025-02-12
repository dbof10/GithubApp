package com.github.di

import com.github.common.utils.DispatchersProvider
import com.github.common.utils.DispatchersProviderImpl
import com.github.common.utils.TimeUtils
import com.github.common.utils.TimeUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatchersProviderImpl()
    }

    @Provides
    fun provideTimeUtils(): TimeUtils {
        return TimeUtilsImpl()
    }
}
