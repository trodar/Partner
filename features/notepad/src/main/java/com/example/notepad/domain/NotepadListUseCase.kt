package com.example.notepad.domain

import com.trodar.domain.notepad.NotepadEntity
import com.trodar.room.NotepadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotepadListUseCase @Inject constructor(
    private val notepadRepository: NotepadRepository
) {
    suspend fun getNotepadList(): Flow<List<NotepadEntity>> {
        return notepadRepository.getList().map {list ->
            list.map {item ->
                NotepadEntity(
                    id = item.id,
                    txt = item.txt,
                    date = item.date
                )
            }
        }
    }
}