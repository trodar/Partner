package com.trodar.room.preach

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trodar.common.Core
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface PreachDao {

    @Query(
        "Select sum(time) as sumTime, sum(publication) as sumPublication, sum(video) as sumVideo, " +
                "sum(returns) as sumReturns, sum(study) as sumStudy, " +
                "strftime('%Y', datetime(date/1000, 'unixepoch')) as year, " +
                "strftime('%m', datetime(date/1000, 'unixepoch')) as month " +
                "from ${Core.databaseConst.PREACH_TABLE_NAME} " +
                "where strftime('%Y%m', datetime(date/1000, 'unixepoch')) between :minDate and :maxDate"
    )
    fun getYearPeriodList(minDate: String, maxDate: String): Flow<RoomPreachYearsPeriodTuple>

    @Query(
        "Select sum(time) as sumTime, sum(publication) as sumPublication, sum(video) as sumVideo, " +
                "sum(returns) as sumReturns, sum(study) as sumStudy, " +
                "strftime('%Y', datetime(date/1000, 'unixepoch')) as year , " +
                "strftime('%m', datetime(date/1000, 'unixepoch')) as month " +
                "from ${Core.databaseConst.PREACH_TABLE_NAME} " +
                "where strftime('%Y%m', datetime(date/1000, 'unixepoch')) between :minDate and :maxDate " +
                "group by year, month " +
                "order by year desc, month desc"
    )
    fun getMonthsPeriodList(minDate: String, maxDate: String): Flow<List<PreachMonthPeriodTuple>>

    @Query("Select * from ${Core.databaseConst.PREACH_TABLE_NAME} where " +
            "strftime('%Y%m', datetime(date/1000, 'unixepoch')) = :date")
    fun getPreachesOfMonth(date: String): Flow<List<PreachDbEntity>>

    @Query("Select * from ${Core.databaseConst.PREACH_TABLE_NAME} where id = :preachId" )
    fun getPreach(preachId: Int): Flow<PreachDbEntity>

    @Query("Select min(date) from ${Core.databaseConst.PREACH_TABLE_NAME}")
    fun getMinDate(): Date?

    @Query("Select sum(time) as sumTime " +
            "from preach_table " +
            "where strftime('%Y%m', datetime(date/1000, 'unixepoch')) between :minDate and :maxDate")
    suspend fun getSumHoursYearPeriod(minDate: String, maxDate: String): Int

    @Query("Select * from ${Core.databaseConst.PREACH_TABLE_NAME}")
    fun getAllData(): Flow<List<PreachDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreach(preach: PreachDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreaches(preaches: List<PreachDbEntity>)

    @Delete
    suspend fun deletePreach(preach: PreachDbEntity)

    @Query("DELETE FROM ${Core.databaseConst.PREACH_TABLE_NAME}")
    suspend fun deleteAll()

}