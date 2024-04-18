package com.trodar.domain.preach.entity

import com.trodar.room.model.PreachYearsPeriod
import com.trodar.room.preach.PreachMonthPeriodTuple
import java.util.Date

data class PreachYearsPeriodEntity(
    val maxYear: Int,
    val minYear: Int,
    val list: List<PreachMonthPeriodEntity> = emptyList(),
    override val publication: Int,
    override val video: Int,
    override val returns: Int,
    override val study: Int,
    override val hours: Int
): PreachYearsPeriod {
    val titlePeriod: String
        get() = "$maxYear - $minYear"
}

fun getPreviewYearList() = listOf(
    PreachYearsPeriodEntity(
        2023,
        2022,
        getPreviewMonthList(),
        1,
        2,
        0,
        0,
        98
    ),
    PreachYearsPeriodEntity(
        2022,
        2021,
        emptyList(),
        1,
        2,
        0,
        0,
        298
    ),
    PreachYearsPeriodEntity(
        2021,
        2020,
        emptyList(),
        1,
        2,
        0,
        0,
        165
    ),
    PreachYearsPeriodEntity(
        2020,
        2019,
        getPreviewMonthList(),
        1,
        2,
        0,
        0,
        253
    )
)


fun getPreviewMonthList() = listOf(
    PreachMonthPeriodEntity(
        preachMonthPeriodTuple =  PreachMonthPeriodTuple("2023", "09", 55, 1, 1, 0, 0),
        simplePreachEntity = SimplePreachEntity(1, true, 0, Date())
    ),
    PreachMonthPeriodEntity(
        preachMonthPeriodTuple =  PreachMonthPeriodTuple("2023", "10", 125, 0,0, 3, 0),
        simplePreachEntity = SimplePreachEntity()
    ),
    PreachMonthPeriodEntity(
        preachMonthPeriodTuple =  PreachMonthPeriodTuple("2023", "11", 32, 0,0, 0, 0),
        simplePreachEntity = SimplePreachEntity()
    ),
    PreachMonthPeriodEntity(
        preachMonthPeriodTuple =  PreachMonthPeriodTuple("2023", "12", 125, 5,2, 0, 0),
        simplePreachEntity = SimplePreachEntity()
    ),


    )