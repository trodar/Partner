package com.trodar.common_impl.di

import android.content.Context
import android.content.SharedPreferences
import com.trodar.common.Core
import com.trodar.common.Repository
import com.trodar.common_impl.PreachSharedRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    @Provides
    fun sharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(Core.preachConst.APP_PREFERENCES, Context.MODE_PRIVATE)
}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun repositoryBinds(
        preachRepository: PreachSharedRepository
    ): Repository

}