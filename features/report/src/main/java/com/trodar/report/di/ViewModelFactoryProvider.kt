package com.trodar.report.di

import com.trodar.report.presentation.DayListViewModel
import com.trodar.report.presentation.PreachEditViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    fun dayListViewModelFactory(): DayListViewModel.Factory
    fun preachEditViewModelFactory(): PreachEditViewModel.Factory
}