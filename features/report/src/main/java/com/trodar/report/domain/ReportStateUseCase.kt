package com.trodar.report.domain

import com.trodar.room.PreachRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReportStateUseCase @Inject constructor(
    private val preachRepository: PreachRepository
) {
    fun  observeYearListExpanded(): Flow<Set<String>> = preachRepository.observeYearListExpanded()

    suspend fun toggleYearListExpanded(yearMonth: String){
        preachRepository.toggleYearListExpanded(yearMonth)
    }

    suspend fun toggleYearListClear() {
        preachRepository.toggleYearListClear()
    }
}