package com.example.notepad.domain

import com.trodar.domain.notepad.NotepadEntity
import com.trodar.room.NotepadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class NotepadItemUseCase @Inject constructor(
    private val notepadRepository: NotepadRepository
) {
    suspend operator fun invoke(id: Int): Flow<NotepadEntity> {
        return notepadRepository.getItem(id).map { item ->
            if (item != null) NotepadEntity(
                id = item.id,
                txt = item.txt,
                date = item.date
            )
            else NotepadEntity(0, "", Date())
        }
    }
}