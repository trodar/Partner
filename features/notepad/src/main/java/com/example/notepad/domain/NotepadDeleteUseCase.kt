package com.example.notepad.domain

import com.trodar.room.NotepadRepository
import com.trodar.room.model.Notepad
import javax.inject.Inject

class NotepadDeleteUseCase @Inject constructor(
    private val notepadRepository: NotepadRepository
){
    suspend fun delete(notepad: Notepad){
        notepadRepository.deleteNotepad(notepad)
    }
}