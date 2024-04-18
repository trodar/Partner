package com.trodar.common

import com.trodar.common.entity.CountEntity
import kotlinx.coroutines.flow.StateFlow

interface Repository{
    fun String.put(long: Long): Repository
    fun String.put(int: Int): Repository
    fun String.remove(): Repository
    fun String.getInt(): Int
    fun String.getLong(): Long
    fun setMinutes(minutes: Int): Repository
    fun setStartTime(time: Long): Repository
    fun setAlarm(minutes: Int): Repository
    fun setPublication(value: Int): Repository
    fun setReturn(value: Int): Repository
    fun setStudy(value: Int): Repository
    fun setVideo(value: Int): Repository

    fun removeStartTime(): Repository
    fun removeMinutes(): Repository
    fun removeAlarm(): Repository
    fun removePublication(): Repository
    fun removeReturn(): Repository
    fun removeStudy(): Repository
    fun removeVideo(): Repository

    fun getStartTime(): Long
    fun getMinutes(): Int
    fun getAlarm(): Int
    fun getPublication(): Int
    fun getReturn(): Int
    fun getStudy(): Int
    fun getVideo(): Int
    suspend fun preachData(): StateFlow<CountEntity>
}