package com.trodar.report.presentation

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.trodar.common.recources.K
import com.trodar.domain.preach.entity.SimplePreachEntity
import com.trodar.domain.preach.entity.getPreviewList
import com.trodar.report.di.dayListViewModelCreator
import com.trodar.room.model.Preach
import com.trodar.room.model.SimplePreach
import com.trodar.theme.themes.JWPartnerTheme
import com.trodar.utils.feature.BackButton
import com.trodar.utils.feature.SendButton
import com.trodar.utils.feature.SwipeDismiss
import com.trodar.utils.feature.TopAppBar
import com.trodar.utils.feature.getDate
import com.trodar.utils.feature.getMonthByNumber
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayList(
    args: Array<String>,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    dayListViewModel: DayListViewModel = dayListViewModelCreator(DayScreen(args[0], args[1])),
    onDayItemClick: (id: Int) -> Unit,
) {

    val preachList = dayListViewModel.dayListState.collectAsState().value
    val context = LocalContext.current
    DayList(
        preachList = preachList.preach,
        simplePreach = preachList.simplePreach,
        simpleShow = preachList.simpleShow,
        topAppBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIconContent = {
                    BackButton {
                        navController.popBackStack()
                    }
                },
                actions = {
                    SendButton {
                        val message = getReportMessage(
                            context = context,
                            month = dayListViewModel.getMonth(),
                            year = dayListViewModel.getYear(),
                            simplePreach = preachList.simplePreach
                        )
                        sendReport(message, context)

                    }
                }
            )
        },
        onDayItemClick = onDayItemClick,
        scrollBehavior = scrollBehavior,
        onDeletePreachItem = { dayListViewModel.deletePreach(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayList(
    preachList: List<Preach>,
    simplePreach: SimplePreach? = null,
    simpleShow: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    topAppBar: @Composable () -> Unit,
    onDayItemClick: (id: Int) -> Unit,
    onDeletePreachItem: (id: Preach) -> Unit,
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp, vertical = 1.dp)
        .background(MaterialTheme.colorScheme.primaryContainer)
    Scaffold(
        topBar = topAppBar
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val listState = rememberLazyListState()
            LazyColumn(
                state = listState,
                contentPadding = padding,
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                if (simpleShow)
                    item {
                        val preached =
                            if (simplePreach != null && simplePreach.preached) stringResource(com.trodar.theme.R.string.yes) +
                                    ". ${stringResource(com.trodar.theme.R.string.preach_study)}: " +
                                    "${simplePreach.study}"
                            else stringResource(com.trodar.theme.R.string.no)

                        Box(modifier = Modifier.padding(start = 4.dp, top = 2.dp, end = 4.dp)) {
                            Text(
                                text = stringResource(com.trodar.theme.R.string.preached) +
                                        ":  $preached",
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .fillMaxWidth()
                                    .padding(start = 14.dp, top = 4.dp, bottom = 4.dp),
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold,

                                )
                        }
                    }
                items(
                    items = preachList,
                    key = { item -> item.id },
                ) { preachItem ->
                    SwipeDismiss(
                        onDeleteClick = { onDeletePreachItem(preachItem) },
                    ) {
                        ReportItem(
                            modifier = modifier
                                .padding(vertical = 4.dp)
                                .testTag(K.ReportTag.dayItem + preachItem.id),
                            tittleLeft = getDate(preachItem.date, LocalContext.current),
                            tittleRight = getHoursAndMinutes(preachItem.time),
                            bodyLeft1 = preachItem.description,
                            bodyLeft2 = preachItem.getPreachInfo(LocalContext.current)
                        ) {
                            onDayItemClick(preachItem.id)

                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}

fun getReportMessage(
    context: Context,
    month: String,
    year: String,
    simplePreach: SimplePreach
): String {

    val monthName = getMonthByNumber(month.toInt()).uppercase()


    val message = " ${
        context.getString(com.trodar.theme.R.string.simple_preach, "$monthName $year")
    } " + if (simplePreach.preached) {
        context.getString(com.trodar.theme.R.string.yes) +
                if (simplePreach.study > 0) {
                    context.getString(com.trodar.theme.R.string.preach_study) +
                            ": ${simplePreach.study}"
                } else ""

    } else {
        context.getString(com.trodar.theme.R.string.no)
    }
    return message
}

fun sendReport(message: String, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(com.trodar.theme.R.string.send)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Report screen")
@Preview("Report screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DayListPreview() {
    JWPartnerTheme {
        val topAppBarState = rememberTopAppBarState()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

        DayList(
            preachList = getPreviewList(),
        simplePreach = SimplePreachEntity(1, true, 0, Date()),
        simpleShow = true,
        scrollBehavior = scrollBehavior,
        topAppBar = {},
        onDayItemClick = {_ ->},
        onDeletePreachItem = {_ ->},
        )
    }
}


