package com.trodar.room.di

import com.trodar.room.NotepadRepository
import com.trodar.room.preach.RoomPreachRepository
import com.trodar.room.PreachRepository
import com.trodar.room.simplepreach.RoomSimplePreachRepository
import com.trodar.room.SimplePreachRepository
import com.trodar.room.notepad.RoomNotepadRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PreachDataModule {

    @Binds
    fun preachRepository(
        roomPreachRepository: RoomPreachRepository
    ): PreachRepository

    @Binds
    fun simplePreachRepository(
        roomSimplePreachRepository: RoomSimplePreachRepository
    ): SimplePreachRepository

    @Binds
    fun notepadRepository(
        roomNotepadRepository: RoomNotepadRepository
    ): NotepadRepository
}