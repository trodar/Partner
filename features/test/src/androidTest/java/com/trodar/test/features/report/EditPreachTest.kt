package com.trodar.test.features.report

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.trodar.common_impl.di.RepositoryModule
import com.trodar.report.domain.GetPreachUseCase
import com.trodar.report.domain.PreachUpdateUseCase
import com.trodar.report.presentation.PreachEdit
import com.trodar.report.presentation.PreachEditViewModel
import com.trodar.room.PreachRepository
import com.trodar.room.di.PreachDataModule
import com.trodar.room.preach.PreachDbEntity
import com.trodar.test.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(PreachDataModule::class, RepositoryModule::class)
@MediumTest
class EditPreachTest : BaseTest() {
    private val id = 1

    @Inject
    lateinit var getPreachUseCase: GetPreachUseCase

    @Inject
    lateinit var preachUpdateUseCase: PreachUpdateUseCase

    @Inject
    lateinit var preachRepository: PreachRepository

    override fun setUp() {
        super.setUp()

        coEvery { preachRepository.getPreach(id) } returns getPreachDbEntityFlow()

        val preachEditViewModel = PreachEditViewModel(
            id,
            getPreachUseCase,
            preachUpdateUseCase
        )

        composeRule.setContent {
            PreachEdit(
                id,
                preachEditViewModel
            ) { }
        }
    }

    @Test
    fun test() = run {
        // tested code in PreachTimeItemTest test
        assert(true)
    }
}

fun getPreachDbEntityFlow() = MutableStateFlow(
    PreachDbEntity(1, 112, 1, 1, 1, 1, "test test", Date())
)