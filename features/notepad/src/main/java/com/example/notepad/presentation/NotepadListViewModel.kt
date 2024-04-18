package com.example.notepad.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.domain.NotepadDeleteUseCase
import com.example.notepad.domain.NotepadListUseCase
import com.trodar.domain.notepad.NotepadEntity
import com.trodar.room.model.Notepad
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class NotepadState(
    val notepadList: List<NotepadEntity> = emptyList()
)
@HiltViewModel
class NotepadListViewModel @Inject constructor(
    private val notepadListUseCase: NotepadListUseCase,
    private val notepadDeleteUseCase: NotepadDeleteUseCase,
) : ViewModel() {

    private val _notepadListState = MutableStateFlow(NotepadState())
    val notepadState = _notepadListState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            notepadListUseCase.getNotepadList().collect{list ->
                _notepadListState.update {
                    _notepadListState.value.copy(
                        notepadList = list
                    )
                }
            }
        }
    }

    fun delete(notepad: Notepad) {
        viewModelScope.launch {
            notepadDeleteUseCase.delete(notepad)
        }
    }
}