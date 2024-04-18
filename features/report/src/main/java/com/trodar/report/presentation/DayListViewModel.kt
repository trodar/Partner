package com.trodar.report.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.trodar.domain.preach.entity.SimplePreachEntity
import com.trodar.domain.preach.usecase.SimplePreachUseCase
import com.trodar.report.domain.PreachDeleteUseCase
import com.trodar.report.domain.PreachOfMonthUseCase
import com.trodar.room.model.Preach
import com.trodar.utils.BaseScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DayListState(
    val preach: List<Preach> = emptyList(),
    val simplePreach: SimplePreachEntity = SimplePreachEntity(),
    val simpleShow: Boolean = false
)

data class DayScreen(
    val year: String,
    val month: String
) : BaseScreen

class DayListViewModel @AssistedInject constructor(
    @Assisted private val dayScreen: DayScreen,
    private val deleteUseCase: PreachDeleteUseCase,
    private val preachOfMonthUseCase: PreachOfMonthUseCase,
    private val simplePreachUseCase: SimplePreachUseCase
) : ViewModel() {
    private val _dayListState = MutableStateFlow(DayListState())

    init {
        loadPreachOfCurrentMonth(dayScreen)
    }

    val dayListState = _dayListState.asStateFlow()

    fun getMonth() = dayScreen.month
    fun getYear() = dayScreen.year
    fun deletePreach(
        item: Preach
    ) {
        viewModelScope.launch {
            deleteUseCase.deletePreach(item)
        }
    }

    private fun loadPreachOfCurrentMonth(
        dayScreen: DayScreen
    ) {
        viewModelScope.launch {

            val simplePreach = simplePreachUseCase
                .getSimplePreach(dayScreen.year + dayScreen.month)
                .first()
            preachOfMonthUseCase
                .getPreachOfCurrentMonthList(dayScreen.year + dayScreen.month)
                .collect { listPreach ->
                    _dayListState.update {
                        it.copy(
                            preach = listPreach,
                            simplePreach = simplePreach,
                            simpleShow = (dayScreen.year + dayScreen.month >= "202310")
                        )
                    }
                }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(dayScreen: DayScreen): DayListViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            dayScreen: DayScreen
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(dayScreen) as T
            }
        }
    }
}


























