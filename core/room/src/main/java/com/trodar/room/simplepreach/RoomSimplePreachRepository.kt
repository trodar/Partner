package com.trodar.room.simplepreach

import com.trodar.room.SimplePreachRepository
import com.trodar.room.model.SimplePreach
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomSimplePreachRepository @Inject constructor(
   private val simplePreachDao: SimplePreachDao,
): SimplePreachRepository {

    override suspend fun editSimplePreachItem(simplePreach: SimplePreach) {
        simplePreachDao.insertSimplePreach(toSimplePreachDbEntity(simplePreach))
    }

    override suspend fun insertSimplePreaches(simplePreaches: List<SimplePreachDbEntity>) {
        simplePreachDao.insertSimplePreaches(simplePreaches)
    }

    override suspend fun deleteSimplePreach(simplePreach: SimplePreachDbEntity) {
        simplePreachDao.deleteSimplePreach(toSimplePreachDbEntity(simplePreach))
    }

    override suspend fun deleteAll() {
        simplePreachDao.deleteAll()
    }

    override suspend fun getSimplePreachItem(date: String): Flow<SimplePreach?> {
        return simplePreachDao.getSimplePreachItem(date)
    }

    override suspend fun getMonthsPeriodList(
        minDate: String,
        maxDate: String
    ): Flow<List<SimplePreachDbEntity>>? {
        return simplePreachDao.getMonthsPeriodList(minDate, maxDate)
    }

    override suspend fun getAllData(): Flow<List<SimplePreachDbEntity>>? {
        return simplePreachDao.getAllData()
    }

    private fun toSimplePreachDbEntity(preachEntity: SimplePreach): SimplePreachDbEntity {
        return SimplePreachDbEntity(
            preachEntity.id,
            preachEntity.preached,
            preachEntity.study,
            preachEntity.date,
        )
    }
}