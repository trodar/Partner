package com.trodar.common_impl.entity

import com.trodar.common.entity.CountEntity


data class PreachCountDataEntity(
    override val publication: Int = 0,
    override val returns: Int = 0,
    override val study: Int = 0,
    override val video: Int = 0,
    override val startTime: Long = 0,
    override val minutes: Int = 0,
    override val alarm: Int = 0
) : CountEntity

