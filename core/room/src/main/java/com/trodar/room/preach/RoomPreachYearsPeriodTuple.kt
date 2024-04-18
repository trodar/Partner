package com.trodar.room.preach

import androidx.room.ColumnInfo
import com.trodar.room.model.PreachYearsPeriod

data class RoomPreachYearsPeriodTuple(
    @ColumnInfo(name = "sumTime") override val hours: Int,
    @ColumnInfo(name = "sumPublication") override val publication: Int,
    @ColumnInfo(name = "sumVideo") override val video: Int,
    @ColumnInfo(name = "sumReturns") override val returns: Int,
    @ColumnInfo(name = "sumStudy") override val study: Int,
) : PreachYearsPeriod
