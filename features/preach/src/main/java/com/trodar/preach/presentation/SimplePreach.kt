package com.trodar.preach.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trodar.common.recources.K
import com.trodar.domain.preach.entity.SimplePreachEntity
import com.trodar.utils.feature.getMonthByNumber
import java.time.LocalDate


@Composable
fun SimplePreach(
    simplePreachEntity: SimplePreachEntity,
    onSimpleCheckBoxClick: (SimplePreachEntity, Boolean) -> Unit,
    onSimpleCountClick: (SimplePreachEntity, Int) -> Unit,
) {

    val monthName = getMonthByNumber(LocalDate.now().month.value)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier
                .width(340.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = stringResource(com.trodar.theme.R.string.simple_preach, monthName),
                fontSize = 20.sp,
            )
            Checkbox(
                modifier = Modifier.semantics { testTag = K.PreachTag.simpleCheckBox },
                checked = simplePreachEntity.preached,
                onCheckedChange = { onSimpleCheckBoxClick(simplePreachEntity, it) })
        }
        Row(
            modifier = Modifier
                .width(340.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = stringResource(com.trodar.theme.R.string.preach_study),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.size(16.dp))
            MinusButton(
                modifier = Modifier.size(30.dp),
                preachItemTesTag = K.PreachTag.preachItemList[4]
            ) {
                onSimpleCountClick(simplePreachEntity, -1)
            }
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = simplePreachEntity.study.toString(),
                fontSize = 36.sp,
            )
            Spacer(modifier = Modifier.size(12.dp))
            PlusButton(
                modifier = Modifier.size(30.dp),
                preachItemTesTag = K.PreachTag.preachItemList[4]
            ) {
                onSimpleCountClick(simplePreachEntity, 1)
            }
        }
    }
}