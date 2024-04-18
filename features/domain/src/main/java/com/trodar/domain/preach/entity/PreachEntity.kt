package com.trodar.domain.preach.entity

import com.trodar.room.model.Preach
import java.util.Date

data class PreachEntity(
    override val id: Int = 0,
    override val time: Int = 0,
    override val publication: Int = 0,
    override val video: Int = 0,
    override val returns: Int = 0,
    override val study: Int = 0,
    override val description: String = "",
    override val date: Date = Date()
) : Preach



fun getPreviewList() = listOf(
    PreachEntity(1,
        123,
        2,
        4,
        3,
        1,
        "good time today",
        Date()),
    PreachEntity(1,
        123,
        2,
        4,
        3,
        1,
        "good time today",
        Date()),
    PreachEntity(1,
        123,
        2,
        4,
        3,
        1,
        "good time today",
        Date()),
    PreachEntity(1,
        123,
        2,
        4,
        3,
        1,
        "good time today",
        Date()),
)