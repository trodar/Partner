package com.trodar.domain.preach.entity

import com.trodar.room.model.SimplePreach
import java.util.Date

data class SimplePreachEntity(
    override val id: Int = 0,
    override val preached: Boolean = false,
    override val study: Int = 0,
    override val date: Date = Date()
) : SimplePreach