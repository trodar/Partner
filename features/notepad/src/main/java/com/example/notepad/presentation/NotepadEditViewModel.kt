package com.example.notepad.presentation

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.notepad.domain.NotepadEditUseCase
import com.example.notepad.domain.NotepadItemUseCase
import com.trodar.domain.notepad.NotepadEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NotepadEditState(
    val item: NotepadEntity = NotepadEntity(),
    val textFieldValue: TextFieldValue = TextFieldValue(""),
    val edited: Boolean = false
)

class NotepadEditViewModel @AssistedInject constructor(
    @Assisted private val id: Int,
    private val notepadEditUseCase: NotepadEditUseCase,
    private val notepadItemUseCase: NotepadItemUseCase,
) : ViewModel() {

    private var job: Job? = null
    private val _notepadEditState = MutableStateFlow(NotepadEditState())
    val notepadState = _notepadEditState.asStateFlow()

    init {
        loadText(id)
    }

    private fun loadText(id: Int) {

        job = viewModelScope.launch {
            notepadItemUseCase(id).collect { item ->
                _notepadEditState.update {
                    _notepadEditState.value.copy(
                        item = item,
                        textFieldValue = it.textFieldValue.copy( text = item.txt),
                        edited = false
                    )
                }
            }
        }
    }

    fun changeText(value: TextFieldValue) {
        _notepadEditState.update {
            _notepadEditState.value.copy(textFieldValue = value, edited = true)
        }
    }

    fun save() {
        if ( _notepadEditState.value.textFieldValue.text.isBlank()) return

        viewModelScope.launch {
            val notepad = NotepadEntity(
                _notepadEditState.value.item.id,
                _notepadEditState.value.textFieldValue.text,
                _notepadEditState.value.item.date
            )

            if (_notepadEditState.value.item.id == 0) {
                job?.cancel()
                val id = notepadEditUseCase(notepad)
                loadText(id)

            } else
                _notepadEditState.update {
                    notepadEditUseCase(notepad)
                    _notepadEditState.value.copy(
                        edited = false
                    )
                }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): NotepadEditViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            id: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }
}