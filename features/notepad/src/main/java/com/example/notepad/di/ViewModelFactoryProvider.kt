package com.example.notepad.di

import com.example.notepad.presentation.NotepadEditViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun notepadEditViewModelFactory(): NotepadEditViewModel.Factory
}