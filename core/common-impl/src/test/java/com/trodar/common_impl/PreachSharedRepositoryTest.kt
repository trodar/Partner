package com.trodar.common_impl

import android.content.Context
import android.content.SharedPreferences
import io.mockk.every
import io.mockk.excludeRecords
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.verifySequence
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class PreachSharedRepositoryTest {


    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var context: Context

    @RelaxedMockK
    lateinit var preferences: SharedPreferences

    @RelaxedMockK
    lateinit var editor: SharedPreferences.Editor

    private lateinit var repository: PreachSharedRepository

    @Before
    fun setUp() {
        every { context.getSharedPreferences(any(), any()) } returns preferences
        every { preferences.edit() } returns editor

        repository = PreachSharedRepository(context, preferences)
        excludeRecords { preferences.registerOnSharedPreferenceChangeListener(any()) }

    }

    @Test
    fun setPutValuesToPreference() {

        repository.setAlarm(1)
            .setMinutes(2)
            .setPublication(3)
            .setReturn(4)
            .setStudy(5)
            .setStartTime(11)

        verifySequence {
            preferences.edit()
            editor.putInt("ALARM", 1)
            editor.apply()
            preferences.edit()
            editor.putInt("MINUTES", 2)
            editor.apply()
            preferences.edit()
            editor.putInt("PUBLICATION", 3)
            editor.apply()
            preferences.edit()
            editor.putInt("RETURN", 4)
            editor.apply()
            preferences.edit()
            editor.putInt("STUDY", 5)
            editor.apply()
            preferences.edit()
            editor.putLong("STARTTIME", 11)
            editor.apply()
        }
    }

    @Test
    fun getValuesFromPreference() {

        every { repository.getAlarm() } returns 1
        every { repository.getStartTime() } returns 12

        val alarm = repository.getAlarm()
        val startTime = repository.getStartTime()

        assertEquals(1, alarm)
        assertEquals(12, startTime)
        verifySequence {
            preferences.getInt("ALARM", 0)
            preferences.getLong("STARTTIME", 0)
        }
    }

    @Test
    fun removeValuesFromPreference() {

        repository.removeAlarm()
        repository.removeMinutes()
        repository.removeStartTime()
        repository.removeStudy()
        repository.removePublication()
        repository.removeReturn()
        repository.removeVideo()
        repository.removeMinutes()

        verifySequence {
            preferences.edit()
            editor.remove("ALARM")
            editor.apply()
            preferences.edit()
            editor.remove("MINUTES")
            editor.apply()
            preferences.edit()
            editor.remove("STARTTIME")
            editor.apply()
            preferences.edit()
            editor.remove("STUDY")
            editor.apply()
            preferences.edit()
            editor.remove("PUBLICATION")
            editor.apply()
            preferences.edit()
            editor.remove("RETURN")
            editor.apply()
            preferences.edit()
            editor.remove("VIDEO")
            editor.apply()
            preferences.edit()
            editor.remove("MINUTES")
            editor.apply()
        }

    }
}