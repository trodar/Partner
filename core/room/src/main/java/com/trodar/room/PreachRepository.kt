package com.trodar.room

import com.trodar.room.model.Preach
import com.trodar.room.preach.PreachDbEntity
import com.trodar.room.preach.PreachMonthPeriodTuple
import com.trodar.room.preach.RoomPreachYearsPeriodTuple
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface PreachRepository {

    suspend fun editPreach(preachDbEntity: Preach)
    suspend fun insertPreaches( preaches: List<PreachDbEntity>)

    suspend fun deletePreach(preachDbEntity: Preach)
    suspend fun deleteAll()

    suspend fun getFirstDate(): Date?
    suspend fun getYearPeriodList(minDate: String, maxDate: String): Flow<RoomPreachYearsPeriodTuple>
    suspend fun getMonthsPeriodList(minDate: String, maxDate: String): Flow<List<PreachMonthPeriodTuple>>
    suspend fun getPreachesOfMonth(date: String): Flow<List<PreachDbEntity>>
    suspend fun getPreach(preachId: Int): Flow<Preach>
    suspend fun getSumHoursYearPeriod(minDate: String, maxDate: String): Int
    suspend fun getAllData(): Flow<List<Preach>>

    suspend fun toggleYearListExpanded(index: String)
    suspend fun toggleYearListClear()

    fun observeYearListExpanded(): Flow<Set<String>>
}