package com.trodar.test.core.room.di

import com.trodar.room.NotepadRepository
import com.trodar.room.PreachRepository
import com.trodar.room.SimplePreachRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mockk.mockk
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestPreachDataModule {
    @Provides
    @Singleton
    fun preachRepository(): PreachRepository {
        return mockk(relaxed = true)
    }

    @Provides
    @Singleton
    fun simplePreachRepository(): SimplePreachRepository{
        return mockk(relaxed = true)
    }

    @Provides
    @Singleton
    fun notepadRepository(): NotepadRepository{
        return mockk(relaxed = true)
    }
}

