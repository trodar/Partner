package com.trodar.common_impl

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.trodar.common.Core
import com.trodar.common.CoreProvider
import com.trodar.common.constants.DataBaseConstant
import com.trodar.common.constants.PreachConstant
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CommonTest{

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var context: Context

    @RelaxedMockK
    lateinit var preferences: SharedPreferences

    @RelaxedMockK
    lateinit var editor: Editor

    private lateinit var repository: PreachSharedRepository
    @Before
    fun setUp() {
        every { context.getSharedPreferences(any(), any()) } returns preferences
        every { preferences.edit() } returns editor

        repository = PreachSharedRepository(context, preferences)

        Core.init(createProvider())
    }

    @Test
    fun coreConstantTest() {
        assertEquals(Core.preachConst, PreachConstant)
        assertEquals(Core.databaseConst, DataBaseConstant)
    }

    @Test
    fun coreRepositoryAssignedTest() {
        assertEquals(repository, Core.preachRepository)
    }

    private fun createProvider(): CoreProvider {
        return JwCoreProvider(CommonTest::class.java, CommonTest::class.java, repository)
    }
}