package com.trodar.report.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trodar.common.recources.K
import com.trodar.domain.preach.entity.PreachYearsPeriodEntity
import com.trodar.utils.feature.AnimatedTopVisibility
import com.trodar.utils.feature.getMonthByNumber


@Composable
fun MonthList(
    modifier: Modifier,
    yearItem: PreachYearsPeriodEntity,
    onMonthHeaderClick: (year: String, month: String) -> Unit,

    ) {
    AnimatedTopVisibility(
        visible = yearItem.list.isNotEmpty(),
    ) {
        RowSpace()
        Column{
            yearItem.list.forEachIndexed {index, monthItem ->

                val simplePreach = if (monthItem.simplePreachEntity?.preached == true)
                    stringResource(com.trodar.theme.R.string.preached)
                else null
                RowSpace()
                ReportItem(
                    modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(start = 16.dp)
                        .testTag(K.ReportTag.monthItem + index.toString()),
                    tittleLeft = getMonthByNumber(monthItem.month.toInt()) + " " +
                            monthItem.year,
                    tittleRight = getHoursAndMinutes(monthItem.hours),
                    bodyLeft1 = simplePreach

                ) {
                    onMonthHeaderClick(monthItem.year, monthItem.month)
                }

            }
        }
    }
}

@Composable
fun RowSpace(height: Dp = 2.dp) {
    Row {
        Spacer(modifier = Modifier.height(height))
    }
}

