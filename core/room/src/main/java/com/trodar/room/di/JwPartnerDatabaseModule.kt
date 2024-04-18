package com.trodar.room.di

import android.app.Application
import androidx.room.Room
import com.trodar.common.Core
import com.trodar.room.JwPartnerDatabase
import com.trodar.room.notepad.NotepadDao
import com.trodar.room.preach.PreachDao
import com.trodar.room.simplepreach.SimplePreachDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JwPartnerDatabaseModule {

    @Provides
    @Singleton
    fun provideJwDataBase(
        application: Application,
    ): JwPartnerDatabase {
        return Room
            .databaseBuilder(application, JwPartnerDatabase::class.java, Core.databaseConst.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePreachDao(jwPartnerDatabase: JwPartnerDatabase): PreachDao {
        return jwPartnerDatabase.preachDao()
    }

    @Provides
    @Singleton
    fun provideSimplePreachDao(jwPartnerDatabase: JwPartnerDatabase): SimplePreachDao {
        return jwPartnerDatabase.simplePreachDao()
    }

    @Provides
    @Singleton
    fun provideNotepadDao(jwPartnerDatabase: JwPartnerDatabase): NotepadDao {
        return jwPartnerDatabase.notepadDao()
    }
}