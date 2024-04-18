package com.trodar.report.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.trodar.common.entity.CountEntity
import com.trodar.domain.preach.entity.PreachCountEntity
import com.trodar.domain.preach.entity.PreachCountItem
import com.trodar.domain.preach.entity.PreachEntity
import com.trodar.report.domain.GetPreachUseCase
import com.trodar.report.domain.PreachUpdateUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

data class PreachEditState(
    val preachCount: PreachCountEntity = PreachCountEntity(),
    val description: String = "",
    val date: Date = Date()
)

class PreachEditViewModel @AssistedInject constructor(
    @Assisted private val preachId: Int,
    private val getPreachUseCase: GetPreachUseCase,
    private val preachUpdateUseCase: PreachUpdateUseCase
) : ViewModel() {

    private val _preachState = MutableStateFlow(PreachEditState())
    val preachState = _preachState.asStateFlow()

    init {
        viewModelScope.launch {
            getPreachUseCase(preachId = preachId).collect { preach ->

                _preachState.update {
                    it.copy(
                        preachCount = PreachCountEntity.mapFromPreachCountEntity(preach),
                        description = preach.description,
                        date = preach.date
                    )
                }
            }
        }
    }

    fun updateDescription(value: String) {
        viewModelScope.launch {
            _preachState.update {
                it.copy(
                    description = value
                )
            }
        }
    }

    fun updatePreachData(data: PreachCountItem, value: Int) {
        viewModelScope.launch {
            _preachState.update {
                it.copy(
                    preachCount = it.preachCount.copy(
                        publication =
                        if (data.preachType != CountEntity.PreachType.PUBLICATION) it.preachCount.publication
                        else PreachCountItem(data.preachType, data.count + value, data.textId),

                        study = if (data.preachType != CountEntity.PreachType.STUDY) it.preachCount.study
                        else PreachCountItem(data.preachType, data.count + value, data.textId),

                        video = if (data.preachType != CountEntity.PreachType.VIDEO) it.preachCount.video
                        else PreachCountItem(data.preachType, data.count + value, data.textId),

                        returns = if (data.preachType != CountEntity.PreachType.RETURN) it.preachCount.returns
                        else PreachCountItem(data.preachType, data.count + value, data.textId),

                        minutes = if (data.preachType != CountEntity.PreachType.MINUTES) it.preachCount.minutes
                        else PreachCountItem(data.preachType, data.count + value, data.textId),

                        allMinutes = if (data.preachType != CountEntity.PreachType.ALL_MINUTES) it.preachCount.allMinutes
                        else PreachCountItem(data.preachType, data.count + value, data.textId),
                    )
                )
            }
        }
    }

    fun updatePreach() {
        val preach = PreachEntity(
            id = preachId,
            time = preachState.value.preachCount.allMinutes.count.toInt(),
            publication = preachState.value.preachCount.publication.count.toInt(),
            video = preachState.value.preachCount.video.count.toInt(),
            returns = preachState.value.preachCount.returns.count.toInt(),
            study = preachState.value.preachCount.study.count.toInt(),
            description = preachState.value.description,
            date = preachState.value.date
        )
        viewModelScope.launch {
            preachUpdateUseCase(preach = preach)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(preachId: Int): PreachEditViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            preachId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(preachId) as T
            }
        }
    }
}

