package com.trodar.room

import com.trodar.room.model.Notepad
import com.trodar.room.notepad.NotepadDbEntity
import kotlinx.coroutines.flow.Flow


interface NotepadRepository {

    suspend fun getItem(id: Int): Flow<Notepad?>
    suspend fun getList(): Flow<List<Notepad>>

    suspend fun editNotepad(notepadDbEntity: Notepad): Long
    suspend fun insertNotepads(notepads: List<NotepadDbEntity>)

    suspend fun deleteNotepad(notepad: Notepad)
    suspend fun deleteAll()
}