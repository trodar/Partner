package com.trodar.room.preach

import com.trodar.room.PreachRepository
import com.trodar.room.model.Preach
import com.trodar.room.utils.addOrRemove
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import javax.inject.Inject


class RoomPreachRepository @Inject constructor(
    private val preachDao: PreachDao,
) : PreachRepository {

    private val expandedYearList = MutableStateFlow(setOf<String>())
    override suspend fun editPreach(preachDbEntity: Preach) {
        preachDao.insertPreach(toPreachDbEntity(preachDbEntity))
    }

    override suspend fun insertPreaches(preaches: List<PreachDbEntity>) {
        preachDao.insertPreaches(preaches)
    }

    override suspend fun deletePreach(preachDbEntity: Preach) {
        preachDao.deletePreach(toPreachDbEntity(preachDbEntity))
    }

    override suspend fun deleteAll() {
        preachDao.deleteAll()
    }

    override suspend fun getYearPeriodList(minDate: String, maxDate: String): Flow<RoomPreachYearsPeriodTuple> {
        return  preachDao.getYearPeriodList(minDate, maxDate)
    }

    override suspend fun getMonthsPeriodList(minDate: String, maxDate: String): Flow<List<PreachMonthPeriodTuple>> {
        return preachDao.getMonthsPeriodList(minDate, maxDate)
    }

    override suspend fun getPreachesOfMonth(date: String): Flow<List<PreachDbEntity>> {
        return preachDao.getPreachesOfMonth(date)
    }

    override suspend fun getPreach(preachId: Int): Flow<Preach> {
        return preachDao.getPreach(preachId)
    }

    override suspend fun getSumHoursYearPeriod(minDate: String, maxDate: String): Int {
        return preachDao.getSumHoursYearPeriod(minDate, maxDate)
    }

    override suspend fun getAllData(): Flow<List<Preach>> {
        return preachDao.getAllData()
    }

    override suspend fun toggleYearListExpanded(index: String) {
        expandedYearList.update {
            it.addOrRemove(index)
        }
    }

    override suspend fun toggleYearListClear() {
        expandedYearList.update {
            emptySet()
        }
    }

    override fun observeYearListExpanded(): Flow<Set<String>> = expandedYearList

    override suspend fun getFirstDate(): Date? {
        return preachDao.getMinDate()
    }

    private fun toPreachDbEntity(preachEntity: Preach): PreachDbEntity {
        return PreachDbEntity(
            preachEntity.id,
            preachEntity.time,
            preachEntity.publication,
            preachEntity.video,
            preachEntity.returns,
            preachEntity.study,
            preachEntity.description,
            preachEntity.date,
        )
    }
}