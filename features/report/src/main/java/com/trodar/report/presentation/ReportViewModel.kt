package com.trodar.report.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trodar.common.Core
import com.trodar.common.constants.PreachConstant.TIME_OUT
import com.trodar.domain.preach.entity.PreachYearsPeriodEntity
import com.trodar.report.domain.ListPreachUseCase
import com.trodar.report.domain.ReportStateUseCase
import com.trodar.report.domain.backup.ExportDataBase
import com.trodar.report.domain.backup.ImportDataBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar
import java.util.GregorianCalendar
import javax.inject.Inject


data class ReportState(
    val listPreach: List<PreachYearsPeriodEntity> = emptyList(),
    val loading: Boolean = false
)

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val listPreachUseCase: ListPreachUseCase,
    private val stateUseCase: ReportStateUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    @Inject
    lateinit var exportDataFactory: ExportDataBase.ExportDataBaseFactory

    @Inject
    lateinit var importDataBaseFactory: ImportDataBase.ImportDataBaseFactory
    private val _reportState = MutableStateFlow(ReportState())
    val reportState = _reportState.asStateFlow()

    val expandedYear = stateUseCase.observeYearListExpanded().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(TIME_OUT),
        emptySet()
    )

    init {
        updateList()
    }

    fun updateList() {
        Core.updateNode.reportNode = false
        if ( _reportState.value.loading) return
        isLoad(true)

        _reportState.update {
            it.copy(
                listPreach = emptyList()
            )
        }

        viewModelScope.launch(dispatcher) {
            stateUseCase.toggleYearListClear()
            listPreachUseCase.getYearPeriodList().cancellable().collect { item ->
                _reportState.update {
                    it.copy(
                        listPreach = it.listPreach + item
                    )
                }
                if (_reportState.value.listPreach.size == 1) {
                    toggleYearListExpanded(item.titlePeriod)
                    loadMonthPeriod(0, item)
                }
            }
            isLoad(false)
        }
    }

    fun toggleYearListExpanded(yearMonth: String) {
        viewModelScope.launch(dispatcher) {
            stateUseCase.toggleYearListExpanded(yearMonth)
        }
    }

    fun loadMonthPeriod(yearIndex: Int, yearItem: PreachYearsPeriodEntity) {
        viewModelScope.launch(dispatcher) {
            val minYear = yearItem.minYear
            val maxYear = yearItem.maxYear

            listPreachUseCase.getMonthsPeriodList(minYear, maxYear).collect { item ->
                val yearList = reportState.value.listPreach.toMutableList()

                if (item.isNotEmpty()) {
                    if (yearList[yearIndex].list.isNotEmpty()) {
                        val hours = listPreachUseCase.getSumHoursYearPeriod(
                            yearList[yearIndex].minYear.toString(),
                            yearList[yearIndex].maxYear.toString()
                        )
                        yearList[yearIndex] = yearList[yearIndex].copy(list = item, hours = hours)
                    } else {
                        yearList[yearIndex] = yearList[yearIndex].copy(list = item)
                    }
                } else {
                    yearList[yearIndex] = yearList[yearIndex].copy(list = item, hours = 0)
                }


                _reportState.value = _reportState.value.copy(listPreach = yearList.toList())
            }
        }
    }

    fun exportDataBase(context: Context): Intent {
        isLoad(true)
        val calendar: Calendar = GregorianCalendar()
        val filename = String.format(
            "%s_%d-%02d-%02d-%02d-%02d-%02d_%s_%s.zip",
            context.getString(com.trodar.theme.R.string.app_name),
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH] + 1,
            calendar[Calendar.DAY_OF_MONTH],
            calendar[Calendar.HOUR_OF_DAY],
            calendar[Calendar.MINUTE],
            calendar[Calendar.SECOND],
            Build.MANUFACTURER,
            Build.MODEL
        )
        val file = File(context.cacheDir, filename)
        val outputStream = FileOutputStream(file)

        try {
            val export = exportDataFactory.create(outputStream)
            export.run()
            outputStream.flush()
            outputStream.close()
        } finally {
            isLoad(false)
        }

        val contentUri = FileProvider.getUriForFile(
            context,
            "com.trodar.file-provider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        shareIntent.setType("application/zip")
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        return shareIntent
    }

    fun importDataBase(context: Context, uri: Uri): Boolean {
        isLoad(true)
        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
        try {
            val fileDescriptor = parcelFileDescriptor?.fileDescriptor ?: return false
            val inputStream = FileInputStream(fileDescriptor)
            val import = importDataBaseFactory.create(inputStream)
            val result = import.run()

            inputStream.close()
            return result

        } catch (e: IOException) {
            return false
        } catch (e: Exception) {
            return false
        } finally {
            parcelFileDescriptor?.close()
            Core.updateNode.notepadNode = true
            Core.updateNode.preachNode = true
            isLoad(false)
        }
    }

    private fun isLoad(value: Boolean) {
        _reportState.update {
            it.copy(
                loading = value
            )
        }
    }
}































