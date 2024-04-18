package com.trodar.common.entity

interface CountEntity {

    val publication: Int
    val returns: Int
    val study: Int
    val video: Int
    val startTime: Long
    val minutes: Int
    val alarm: Int


    enum class PreachType {
        PUBLICATION,
        RETURN,
        STUDY,
        VIDEO,
        START_TIME,
        // minutes when paused time
        MINUTES,
        ALARM,
        // minutes + starttime
        ALL_MINUTES
    }
}