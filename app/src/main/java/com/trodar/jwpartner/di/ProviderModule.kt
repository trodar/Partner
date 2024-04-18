package com.trodar.jwpartner.di

import com.trodar.common.CoreProvider
import com.trodar.common.Repository
import com.trodar.common_impl.JwCoreProvider
import com.trodar.jwpartner.MainActivity
import com.trodar.preach.domain.PreachService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {
    @Provides
    fun coreProvider (
        preachRepository: Repository
    ): CoreProvider {
        return JwCoreProvider(MainActivity::class.java, PreachService::class.java, preachRepository)
    }
}