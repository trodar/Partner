package com.trodar.preach.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trodar.common.recources.K
import com.trodar.domain.preach.entity.PreachCountEntity
import com.trodar.domain.preach.entity.PreachCountItem
import com.trodar.domain.preach.entity.SimplePreachEntity
import com.trodar.preach.domain.PreachResource
import com.trodar.preach.domain.PreachService
import com.trodar.theme.colors.DarkLineBoxGradient
import com.trodar.theme.colors.LightLineBoxGradient
import com.trodar.theme.themes.JWPartnerTheme
import com.trodar.utils.feature.TopAppBar
import com.trodar.utils.feature.showShortToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreachScreen(
    preachViewModel: PreachViewModel = hiltViewModel(),
    scrollBehavior: TopAppBarScrollBehavior
) {
    val preachData by preachViewModel.preachState.collectAsState()
    val service = preachData.binder.data?.getService()
    val context = LocalContext.current

    PreachScreen(
        topAppBar = {TopAppBar(scrollBehavior= scrollBehavior)},
        binder = preachData.binder,
        preachCount = preachData.preachCount,
        description = preachData.description,
        simplePreachEntity = preachData.simplePreach,
        scrollBehavior = scrollBehavior,
        onStartService = {
            preachViewModel.startService(it)
        },
        onPauseService = {
            service?.setPaused()
            preachViewModel.setPause(service!!.getPaused())
        },
        onStopService = {
            service?.stop()
            preachViewModel.finishService()

        },
        onCancelService = {
            preachViewModel.cancel()
            showShortToast(context, context.getString(com.trodar.theme.R.string.canceled))
        },
        onPlusClick = { data, count -> preachViewModel.updatePreachData(data, count) },
        onMinusClick = { data, count ->
            if (data.count > 0)
                preachViewModel.updatePreachData(data, count)
        },
        onSaveClick = {
            if (preachData.preachCount.allMinutes.count == 0L) {
                preachViewModel.cancel()
                showShortToast(context, context.getString(com.trodar.theme.R.string.preach_save_zero))
            } else {
                preachViewModel.insertPreach()
                showShortToast(context,context.getString(com.trodar.theme.R.string.preach_saved))
            }
        },
        onUpdateTime = { preachViewModel.updateTime() },
        onValueChange = { value -> preachViewModel.updateDescription(value) },
        onSimpleCheckBoxClick = { _, checked ->
            preachViewModel.updateSimplePreach(checked, 0)
        },
        onSimpleCountClick = { item, count ->
            if (item.study + count >= 0)
                preachViewModel.updateSimplePreach(item.preached, count)
        },

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreachScreen(
    topAppBar: @Composable () -> Unit,
    binder: PreachResource<PreachService.LocalBinder>,
    preachCount: PreachCountEntity,
    description: String,
    simplePreachEntity: SimplePreachEntity,
    scrollBehavior: TopAppBarScrollBehavior,
    onStartService: (Boolean) -> Unit,
    onPauseService: () -> Unit,
    onStopService: () -> Unit,
    onCancelService: () -> Unit,
    onPlusClick: (PreachCountItem, count: Int) -> Unit,
    onMinusClick: (PreachCountItem, count: Int) -> Unit,
    onSaveClick: () -> Unit,
    onUpdateTime: () -> Unit,
    onValueChange: (String) -> Unit,
    onSimpleCheckBoxClick: (SimplePreachEntity, Boolean) -> Unit,
    onSimpleCountClick: (SimplePreachEntity, Int) -> Unit,
) {
    Scaffold(
        topBar = topAppBar
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(bottom = 100.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .semantics { testTag = K.Screens.preachScreen },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val enabled = binder.status != PreachResource.Status.DEFAULT

            item {
                TimeItem(
                    status = binder.status,
                    data = preachCount.minutes,
                    allMinutes = preachCount.allMinutes,
                    enabled = enabled,
                    onPlusClick = onPlusClick,
                    onMinusClick = onMinusClick,
                    onUpdateTime = onUpdateTime,
                )
            }
            item {
                ServiceItem(
                    status = binder.status,
                    description = description,
                    onStartService = onStartService,
                    onPauseService = onPauseService,
                    onStopService = onStopService,
                    onCancelService = onCancelService,
                    onSaveClick = onSaveClick,
                    onValueChange = onValueChange,
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                colorStops = if (isSystemInDarkTheme())
                                    DarkLineBoxGradient
                                else LightLineBoxGradient
                            )
                        )
                )
            }

            item {

                PreachItem(
                    data = preachCount.publication,
                    enabled = enabled,
                    preachItemTesTag = K.PreachTag.preachItemList[0],
                    onPlusClick = onPlusClick,
                    onMinusClick = onMinusClick,
                )
                PreachItem(
                    data = preachCount.returns,
                    enabled = enabled,
                    preachItemTesTag = K.PreachTag.preachItemList[1],
                    onPlusClick = onPlusClick,
                    onMinusClick = onMinusClick,
                )
                PreachItem(
                    data = preachCount.video,
                    enabled = enabled,
                    preachItemTesTag = K.PreachTag.preachItemList[2],
                    onPlusClick = onPlusClick,
                    onMinusClick = onMinusClick,
                )
                PreachItem(
                    data = preachCount.study,
                    enabled = enabled,
                    preachItemTesTag = K.PreachTag.preachItemList[3],
                    onPlusClick = onPlusClick,
                    onMinusClick = onMinusClick,
                )
            }
            item {
                SimplePreach(
                    simplePreachEntity = simplePreachEntity,
                    onSimpleCheckBoxClick = onSimpleCheckBoxClick,
                    onSimpleCountClick = onSimpleCountClick,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Preach screen")
@Preview("Preach screen (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewPreach(
    binder: PreachResource<PreachService.LocalBinder> = PreachResource.defaults(null),
    preachCountState: PreachCountEntity = PreachCountEntity(),
    simplePreachEntity: SimplePreachEntity = SimplePreachEntity()

) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    JWPartnerTheme {
        PreachScreen(
            {},
            binder,
            preachCountState,
            "city",
            simplePreachEntity,
            scrollBehavior,
            {},
            {},
            {},
            {},
            { _, _ -> },
            { _, _ -> },
            {},
            {},
            {},
            {_, _ -> },
            {_, _ -> },
        )
    }
}








