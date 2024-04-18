package com.trodar.test

import android.Manifest
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.params.FlakySafetyParams
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.trodar.common.Core
import com.trodar.common.CoreProvider
import com.trodar.common.constants.PreachConstant.TIME_OUT
import dagger.hilt.android.testing.HiltAndroidRule
import io.mockk.junit4.MockKRule
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject


open class BaseTest(
    kaspressoBuilder: Kaspresso.Builder = Kaspresso.Builder.withComposeSupport(
        customize = {
            flakySafetyParams = FlakySafetyParams.custom(timeoutMs = TIME_OUT, intervalMs = 1000)
        },
        lateComposeCustomize = { composeBuilder ->
            composeBuilder.semanticsBehaviorInterceptors = mutableListOf()
        })
) : TestCase(
    kaspressoBuilder = kaspressoBuilder
) {
    @get:Rule
    val composeRule = createComposeRule()

    @get:Rule
    val mockKRule = MockKRule(this)

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var coreProvider: CoreProvider

    @Before
    open fun setUp() {
        hiltRule.inject()
        Core.init(coreProvider)
    }

}
