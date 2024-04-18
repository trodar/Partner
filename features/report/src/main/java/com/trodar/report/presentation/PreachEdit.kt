package com.trodar.report.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.trodar.common.recources.K
import com.trodar.domain.preach.entity.PreachCountItem
import com.trodar.preach.domain.PreachResource
import com.trodar.preach.presentation.PreachItem
import com.trodar.preach.presentation.ServiceItem
import com.trodar.preach.presentation.TimeItem
import com.trodar.report.di.preachEditViewModelCreator
import com.trodar.theme.themes.JWPartnerTheme

@Composable
fun PreachEdit(
    preachId: Int,
    preachEditViewModel: PreachEditViewModel = preachEditViewModelCreator(preachId),
    onCancelClick: () -> Unit
) {

    val preachEditState by preachEditViewModel.preachState.collectAsState()

    PreachEdit(
        preachEditState = preachEditState,
        onPlusClick = { data, count -> preachEditViewModel.updatePreachData(data, count) },
        onMinusClick = { data, count ->
            if (data.count + count >= 0)
                preachEditViewModel.updatePreachData(data, count)
        },
        onSaveClick = {
            preachEditViewModel.updatePreach()
            onCancelClick()
        },
        onCancelClick = onCancelClick,
        onDescriptionClick = { value -> preachEditViewModel.updateDescription(value) }
    )
}

@Composable
fun PreachEdit(
    preachEditState: PreachEditState,
    onPlusClick: (PreachCountItem, count: Int) -> Unit,
    onMinusClick: (PreachCountItem, count: Int) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDescriptionClick: (String) -> Unit
) {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                TimeItem(status = PreachResource.Status.FINISH,
                    data = preachEditState.preachCount.allMinutes,
                    allMinutes = preachEditState.preachCount.allMinutes,
                    enabled = true,
                    onPlusClick = onPlusClick,
                    onMinusClick = onMinusClick,
                    onUpdateTime = {})
                ServiceItem(
                    status = PreachResource.Status.FINISH,
                    description = preachEditState.description,
                    onStartService = {},
                    onPauseService = { },
                    onStopService = { },
                    onCancelService = onCancelClick,
                    onSaveClick = onSaveClick,
                    onValueChange = onDescriptionClick,
                )
                PreachItem(
                    data = preachEditState.preachCount.publication,
                    preachItemTesTag = K.PreachTag.preachItemList[0],
                    onPlusClick = onPlusClick,
                    onMinusClick = onMinusClick,
                )
                PreachItem(
                    data = preachEditState.preachCount.returns,
                    preachItemTesTag = K.PreachTag.preachItemList[1],
                    onPlusClick = onPlusClick,
                    onMinusClick = onMinusClick,
                )
                PreachItem(
                    data = preachEditState.preachCount.video,
                    preachItemTesTag = K.PreachTag.preachItemList[2],
                    onPlusClick = onPlusClick,
                    onMinusClick = onMinusClick,
                )
                PreachItem(
                    data = preachEditState.preachCount.study,
                    preachItemTesTag = K.PreachTag.preachItemList[3],
                    onPlusClick = onPlusClick,
                    onMinusClick = onMinusClick,
                )
            }
        }
    }
}

@Preview("Preach screen", showBackground = true, device = Devices.NEXUS_7_2013)
@Preview(
    "preach item (ru)",
    locale = "ru-rRU",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    device = Devices.NEXUS_7_2013
)
@Composable
fun PreviewPreach() {
    JWPartnerTheme {
        val preach = PreachEditState()
        PreachEdit(preach, { _, _ -> }, { _, _ -> }, {}, {}, { _ -> })
    }
}