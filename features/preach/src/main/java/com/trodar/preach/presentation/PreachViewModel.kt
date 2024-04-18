package com.trodar.preach.presentation

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trodar.common.Core
import com.trodar.common.Repository
import com.trodar.common.entity.CountEntity
import com.trodar.domain.preach.entity.PreachCountEntity
import com.trodar.domain.preach.entity.PreachCountItem
import com.trodar.domain.preach.entity.PreachEntity
import com.trodar.domain.preach.entity.SimplePreachEntity
import com.trodar.domain.preach.usecase.SimplePreachUseCase
import com.trodar.preach.domain.PreachInsertUseCase
import com.trodar.preach.domain.PreachResource
import com.trodar.preach.domain.PreachService
import com.trodar.room.model.Preach
import com.trodar.theme.R
import com.trodar.utils.extension.format
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

data class PreachState(
    val startTime: Long,
    val binder: PreachResource<PreachService.LocalBinder> =
        if (startTime == 0L)
            PreachResource.defaults(data = null)
        else PreachResource.start(data = null),
    val preachCount: PreachCountEntity = PreachCountEntity(),
    val description: String = "",
    val simplePreach: SimplePreachEntity = SimplePreachEntity()
)

@HiltViewModel
class PreachViewModel @Inject constructor(
    private val context: Application,
    private val preachSharedRepository: Repository,
    private val preachInsertUseCase: PreachInsertUseCase,
    private val simplePreachUseCase: SimplePreachUseCase,
) : ViewModel() {

    private val _preachState = MutableStateFlow(PreachState(preachSharedRepository.getStartTime()))
    val preachState = _preachState.asStateFlow()

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, iBinder: IBinder) {
            val binder: PreachService.LocalBinder = iBinder as PreachService.LocalBinder

            if (preachSharedRepository.getStartTime() != 0L
            ) {
                _preachState.value = _preachState.value.copy(binder = PreachResource.start(binder))
            } else if (PreachService.getMinutes() > 0) {
                _preachState.value = _preachState.value.copy(binder = PreachResource.pause(binder))
            } else {
                _preachState.value =
                    _preachState.value.copy(binder = PreachResource.defaults(binder))
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
        }
    }

    init {
        subscribe()

        Intent(context, Core.preachServiceClass).also {
            if (preachSharedRepository.getStartTime() != 0L
            ) {
                context.startService(it)
            }
            context.bindService(it, connection, 0)
        }
    }

    private fun subscribe() {
        viewModelScope.launch {
            preachSharedRepository.preachData().collect { item ->
                _preachState.update {
                    _preachState.value.copy(
                        preachCount = PreachCountEntity.mapFromCountEntity(item)
                    )
                }
            }
        }
        viewModelScope.launch {
            simplePreachUseCase
                .getSimplePreach(LocalDate.now().year.toString() + LocalDate.now().monthValue.format() )
                .collect { item ->
                    _preachState.update {
                        _preachState.value.copy(
                            simplePreach = item
                        )
                    }
                }
        }
    }

    fun updateDescription(value: String) {
        _preachState.value = _preachState.value.copy(description = value)
    }

    fun updatePreachData(data: PreachCountItem, value: Int) {
        viewModelScope.launch {
            when (data.preachType) {
                CountEntity.PreachType.PUBLICATION -> preachSharedRepository.setPublication(data.count.toInt() + value)
                CountEntity.PreachType.RETURN -> preachSharedRepository.setReturn(data.count.toInt() + value)
                CountEntity.PreachType.STUDY -> preachSharedRepository.setStudy(data.count.toInt() + value)
                CountEntity.PreachType.VIDEO -> preachSharedRepository.setVideo(data.count.toInt() + value)
                CountEntity.PreachType.START_TIME -> preachSharedRepository.setStartTime(data.count + value)
                CountEntity.PreachType.MINUTES -> preachSharedRepository.setMinutes(data.count.toInt() + value)
                CountEntity.PreachType.ALARM -> preachSharedRepository.setAlarm(data.count.toInt() + value)
                CountEntity.PreachType.ALL_MINUTES -> {}
            }
        }
    }

    fun updateTime() {
        viewModelScope.launch {
            val minutes = PreachService.getMinutes()
            if (preachState.value.preachCount.allMinutes.count.toInt() != minutes)
                _preachState.value = _preachState.value.copy(
                    preachCount = _preachState.value.preachCount.copy(
                        allMinutes = PreachCountItem(
                            CountEntity.PreachType.ALL_MINUTES,
                            minutes.toLong(),
                            R.string.preach_start
                        )
                    )
                )
        }
    }

    fun startService(isTest: Boolean) {
        viewModelScope.launch {
            preachSharedRepository
                .removeStartTime()
                .setPublication(0)
                .setReturn(0)
                .setStudy(0)
                .setVideo(0)
                .setAlarm(0)
            _preachState.value =
                _preachState.value.copy(binder = PreachResource.start(_preachState.value.binder.data))

            if (!isTest)
                Intent(context, Core.preachServiceClass).also {
                    context.startForegroundService(it)
                    context.bindService(
                        it,
                        connection,
                        0
                    )
                }
        }
    }

    fun finishService() {
        _preachState.value =
            _preachState.value.copy(binder = PreachResource.finish(_preachState.value.binder.data))
    }

    private fun deleteSharedData() {
        viewModelScope.launch {
            preachSharedRepository
                .removeStartTime()
                .removeMinutes()
                .removeAlarm()
                .removeStudy()
                .removeReturn()
                .removePublication()
                .removeVideo()
        }
    }

    fun insertPreach() {
        val preachEntity: Preach = PreachEntity(
            0,
            preachState.value.preachCount.allMinutes.count.toInt(),
            preachState.value.preachCount.publication.count.toInt(),
            preachState.value.preachCount.video.count.toInt(),
            preachState.value.preachCount.returns.count.toInt(),
            preachState.value.preachCount.study.count.toInt(),
            preachState.value.description,
            Date()
        )

        Core.updateNode.notepadNode = true
        Core.updateNode.reportNode = true

        viewModelScope.launch {
            preachInsertUseCase.insertPreach(preachEntity)
            //insertPreachTest()
            deleteSharedData()
            _preachState.value =
                _preachState.value.copy(
                    binder = PreachResource.defaults(_preachState.value.binder.data),
                    description = "",
                )
        }

    }

    fun cancel() {
        deleteSharedData()
        _preachState.value =
            _preachState.value.copy(binder = PreachResource.defaults(_preachState.value.binder.data))

    }

    override fun onCleared() {
        super.onCleared()

        context.unbindService(connection)
    }

    fun setPause(value: Boolean) {
        if (value) _preachState.value =
            _preachState.value.copy(binder = PreachResource.pause(_preachState.value.binder.data))
        else _preachState.value =
            _preachState.value.copy(binder = PreachResource.start(_preachState.value.binder.data))
    }

    fun updateSimplePreach(checked: Boolean, count: Int) {
        val value = _preachState.value.simplePreach.copy(
            preached = checked,
            study = _preachState.value.simplePreach.study + count
        )
        Core.updateNode.reportNode = true
        viewModelScope.launch {
            simplePreachUseCase.editSimplePreachItem(value)
        }

    }

    // fill database with fake data
    private fun insertPreachTest() {
        val count = 1900

        val publicationCount = List(count) { Random.nextInt(0, 10) }
        val videoCount = List(count) { Random.nextInt(0, 10) }
        val returnCount = List(count) { Random.nextInt(0, 10) }
        val studyCount = List(count) { Random.nextInt(0, 10) }
        val minutesCount = List(count) { Random.nextInt(30, 120) }
        var date = LocalDateTime.of(2013, 1, 1, 8, 10)
        val dateCount = List(count) {
            date = date.plusDays(Random.nextLong(0, 5)).plusHours(Random.nextLong(0, 10))
            date
        }

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm")
        val description = "Fill fake data for "

        viewModelScope.launch {

            for (i in 0 until count) {

                val preachEntity: Preach = PreachEntity(
                    0,
                    minutesCount[i],
                    publicationCount[i],
                    videoCount[i],
                    returnCount[i],
                    studyCount[i],
                    "$description  ${dateCount[i].format(formatter)}",
                    Date.from(dateCount[i].toInstant(ZoneOffset.UTC))

                )
                preachInsertUseCase.insertPreach(preachEntity)
            }
        }

    }
}
