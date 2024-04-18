package com.trodar.room.notepad

import com.trodar.room.NotepadRepository
import com.trodar.room.model.Notepad
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomNotepadRepository @Inject constructor(
    private val notepadDao: NotepadDao
) : NotepadRepository {
    override suspend fun getItem(id: Int): Flow<Notepad?> {
        return notepadDao.getItem(id)
    }

    override suspend fun getList(): Flow<List<Notepad>> {
        return notepadDao.getList()
    }

    override suspend fun editNotepad(notepad: Notepad): Long {
        return notepadDao.editNotepad(
            notepad.let {
                NotepadDbEntity(
                    it.id,
                    it.txt,
                    it.date
                )
            }
        )
    }

    override suspend fun insertNotepads(notepads: List<NotepadDbEntity>) {
        notepadDao.insertNotepads(notepads)
    }

    override suspend fun deleteNotepad(notepad: Notepad) {
        notepadDao.deleteNotepad(notepad.let {
            NotepadDbEntity(
                it.id,
                it.txt,
                it.date
            )
        })
    }

    override suspend fun deleteAll() {
        notepadDao.deleteAll()
    }
}