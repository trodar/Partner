package com.trodar.room

import com.trodar.room.simplepreach.SimplePreachDbEntity
import com.trodar.room.model.SimplePreach
import kotlinx.coroutines.flow.Flow

interface SimplePreachRepository {
    suspend fun getSimplePreachItem(date: String): Flow<SimplePreach?>

    suspend fun getMonthsPeriodList(minDate: String, maxDate: String): Flow<List<SimplePreachDbEntity>>?
    suspend fun getAllData(): Flow<List<SimplePreachDbEntity>>?

    suspend fun editSimplePreachItem(simplePreach: SimplePreach)
    suspend fun insertSimplePreaches(simplePreaches: List<SimplePreachDbEntity>)

    suspend fun deleteSimplePreach(simplePreach: SimplePreachDbEntity)
    suspend fun deleteAll()

}