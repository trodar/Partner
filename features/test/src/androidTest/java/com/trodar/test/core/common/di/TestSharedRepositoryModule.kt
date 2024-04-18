package com.trodar.test.core.common.di

import com.trodar.common.CoreProvider
import com.trodar.common.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mockk.mockk
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestSharedRepositoryModule {

    @Provides
    @Singleton
    fun sharedRepository(): Repository {
        return mockk(relaxed = true)
    }
    @Provides
    @Singleton
    fun coreProviderRepository(): CoreProvider {
        return mockk(relaxed = true)
    }

}