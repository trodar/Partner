package com.example.notepad.di

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notepad.presentation.NotepadEditViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
fun notepadEditViewModelCreator(
    id: Int
) : NotepadEditViewModel{
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).notepadEditViewModelFactory()

    return viewModel(
        factory = NotepadEditViewModel.provideFactory(factory, id)
    )
}