package com.trodar.report.presentation

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trodar.common.Core
import com.trodar.common.constants.PreachConstant.SIXTY_MIN
import com.trodar.common.recources.K
import com.trodar.domain.preach.entity.PreachYearsPeriodEntity
import com.trodar.domain.preach.entity.getPreviewYearList
import com.trodar.report.domain.backup.rememberExportBackupActivity
import com.trodar.report.domain.backup.rememberImportBackupActivity
import com.trodar.theme.R
import com.trodar.theme.themes.JWPartnerTheme
import com.trodar.utils.feature.CustomCircularProgressBar
import com.trodar.utils.feature.ExportButton
import com.trodar.utils.feature.TopAppBar
import com.trodar.utils.feature.alertDialog
import com.trodar.utils.feature.showLongToast
import com.trodar.utils.feature.showShortToast
import java.io.IOException


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    reportViewModel: ReportViewModel = hiltViewModel(),
    scrollBehavior: TopAppBarScrollBehavior,
    onMonthClick: (year: String, month: String) -> Unit,
) {
    val context = LocalContext.current
    val remExport = rememberExportBackupActivity(context)
    val remImport = rememberImportBackupActivity {
        reportViewModel.importDataBase(context, it)
        reportViewModel.updateList()
        showShortToast(context, context.getString(R.string.backup_restored))
    }

    val reportState = reportViewModel.reportState.collectAsState().value
    val expandedItems by reportViewModel.expandedYear.collectAsStateWithLifecycle()
    if (Core.updateNode.reportNode) {
        reportViewModel.updateList()
    }

    if (reportState.loading) {
        CustomCircularProgressBar()
    }

    ReportScreen(
        backupImport = remImport,
        reportState = reportState,
        expandedItems = expandedItems,
        scrollBehavior = scrollBehavior,
        onMonthClick = onMonthClick,
        onExportClick = {
            try {
                val intent = reportViewModel.exportDataBase(context)
                remExport.launch(intent)
            } catch (e: IOException) {
                showLongToast(
                    context,
                    context.getString(R.string.backup_failed)
                )
            }
        },
        onReportItemClick = { yearIndex, yearItem ->
            reportViewModel.toggleYearListExpanded(yearItem.titlePeriod)
            if (yearItem.list.isEmpty())
                reportViewModel.loadMonthPeriod(
                    yearIndex,
                    yearItem
                )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    backupImport: ManagedActivityResultLauncher<Array<String>, Uri?>,
    reportState: ReportState,
    expandedItems: Set<String>,
    scrollBehavior: TopAppBarScrollBehavior,
    onMonthClick: (year: String, month: String) -> Unit,
    onExportClick: () -> Unit,
    onReportItemClick: (Int, PreachYearsPeriodEntity) -> Unit,
) {
    val context = LocalContext.current
    val openAlertDialog = remember { mutableStateOf(false) }
    val modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primaryContainer)

    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                actions = {
                    ExportButton(
                        context = context,
                        onExportClick = onExportClick,
                        onImportClick = { openAlertDialog.value = true })
                }
            )
        }
    ) { innerPadding ->

        val listState = rememberLazyListState()



        LazyColumn(
            state = listState,
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(top = 1.dp)
                .testTag(K.Screens.reportScreen),
            contentPadding = innerPadding
        ) {

            reportState.listPreach.forEachIndexed { yearIndex, yearItem ->
                item {

                    ReportItem(
                        modifier = modifier.testTag(K.ReportTag.yearItem + yearIndex.toString()),
                        tittleLeft = stringResource(
                            R.string.report_year_tittle,
                            yearItem.titlePeriod
                        ),
                        tittleRight = getHoursAndMinutes(yearItem.hours),
                    ) { onReportItemClick(yearIndex, yearItem) }
                }
                if (expandedItems.contains(yearItem.titlePeriod))
                    item {
                        MonthList(
                            modifier = modifier,
                            yearItem = yearItem,
                            onMonthHeaderClick = onMonthClick,
                        )
                    }

                item {
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
            item {
                Spacer(
                    modifier = Modifier
                        .height(90.dp)
                        .background(Color.Red)
                )
            }
        }
    }

    if (openAlertDialog.value) {
        alertDialog(
            onDismissRequest = {
                openAlertDialog.value = false
                showShortToast(context, context.getString(R.string.canceled))
            },
            onConfirmation = {
                openAlertDialog.value = false
                backupImport.launch(arrayOf("*/*"))
            },
            dialogTitle = context.getString(R.string.are_you_sure),
            dialogText = context.getString(R.string.backup_restore)
        )

    }

}

@Composable
fun ReportItem(
    modifier: Modifier,
    tittleLeft: String,
    tittleRight: String,
    bodyLeft1: String? = null,
    bodyLeft2: String? = null,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val textWidth = configuration.screenWidthDp.dp - 90.dp

    Row(modifier = modifier
        .padding(horizontal = 16.dp, vertical = 4.dp)
        .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .heightIn(min = 32.dp).width(textWidth),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = tittleLeft,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            if (!bodyLeft1.isNullOrBlank())
                Text(
                    text = bodyLeft1,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            if (!bodyLeft2.isNullOrBlank())
                Text(
                    text = bodyLeft2,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
        }
        Column(
            modifier = Modifier
                .heightIn(
                    min = if (bodyLeft1.isNullOrBlank()) 32.dp
                    else if (bodyLeft2.isNullOrBlank()) 39.dp else 52.dp
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxHeight(),
                text = tittleRight,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

fun getHoursAndMinutes(minutes: Int): String {
    return (minutes / SIXTY_MIN).toString() + ":" + String.format("%02d", minutes % SIXTY_MIN)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Report screen")
@Preview("Report screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewReport() {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val remImport = rememberImportBackupActivity {
    }

    JWPartnerTheme {
        ReportScreen(
            backupImport = remImport,
            reportState = ReportState(
                listPreach = getPreviewYearList()
            ),
            expandedItems = setOf("2023 - 2022", "2020 - 2019"),
            scrollBehavior = scrollBehavior,
            onMonthClick = { _, _ -> },
            onExportClick = {},
            onReportItemClick = { _, _ -> },
        )
    }
}