package com.trodar.domain.preach.entity

import com.trodar.room.model.PreachMonthsPeriod

data class PreachMonthPeriodEntity(
    val preachMonthPeriodTuple: PreachMonthsPeriod,
    val simplePreachEntity: SimplePreachEntity?
): PreachMonthsPeriod by preachMonthPeriodTuple