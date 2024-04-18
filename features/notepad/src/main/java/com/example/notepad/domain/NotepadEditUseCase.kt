package com.example.notepad.domain

import com.trodar.room.NotepadRepository
import com.trodar.room.model.Notepad
import javax.inject.Inject

class NotepadEditUseCase @Inject constructor(
    private val notepadRepository: NotepadRepository
) {
    suspend operator fun invoke(notepad: Notepad): Int {
        return notepadRepository.editNotepad(notepad).toInt()
    }
}