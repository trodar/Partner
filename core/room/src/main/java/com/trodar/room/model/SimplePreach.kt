package com.trodar.room.model

import java.util.Date

interface SimplePreach: java.io.Serializable  {
    val id: Int
    val preached: Boolean
    val study: Int
    val date: Date
}