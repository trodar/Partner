package com.trodar.report.di

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trodar.report.presentation.DayListViewModel
import com.trodar.report.presentation.DayScreen
import com.trodar.report.presentation.PreachEditViewModel
import com.trodar.room.model.Preach
import dagger.hilt.android.EntryPointAccessors


@Composable
fun dayListViewModelCreator(
    //viewModelStoreOwner: ViewModelStoreOwner,
    dayScreen: DayScreen
) : DayListViewModel{
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).dayListViewModelFactory()

    return viewModel(
        //viewModelStoreOwner =  viewModelStoreOwner,
        factory = DayListViewModel.provideFactory(factory, dayScreen)
    )
}

@Composable
fun preachEditViewModelCreator(
    //viewModelStoreOwner: ViewModelStoreOwner,
    preachId: Int
) : PreachEditViewModel{
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).preachEditViewModelFactory()

    return viewModel(
        //viewModelStoreOwner =  viewModelStoreOwner,
        factory = PreachEditViewModel.provideFactory(factory, preachId)
    )
}