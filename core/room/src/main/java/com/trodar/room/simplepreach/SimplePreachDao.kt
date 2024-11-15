package com.trodar.room.simplepreach

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trodar.common.Core
import kotlinx.coroutines.flow.Flow

@Dao
interface SimplePreachDao {

    @Query("Select * from simple_preach_table" +
            " where strftime('%Y%m', datetime(date/1000, 'unixepoch')) = :date")
    fun getSimplePreachItem(date: String): Flow<SimplePreachDbEntity?>

    @Query("Select * from simple_preach_table" +
            " where strftime('%Y%m', datetime(date/1000, 'unixepoch')) between :minDate and :maxDate")
    fun getMonthsPeriodList(minDate: String, maxDate: String): Flow<List<SimplePreachDbEntity>>?

    @Query("Select * from simple_preach_table" )
    fun getAllData(): Flow<List<SimplePreachDbEntity>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimplePreach(simplePreach: SimplePreachDbEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimplePreaches(simplePreaches: List<SimplePreachDbEntity>)

    @Delete
    suspend fun deleteSimplePreach(simplePreach: SimplePreachDbEntity)
    @Query("DELETE FROM simple_preach_table")
    suspend fun deleteAll()
}