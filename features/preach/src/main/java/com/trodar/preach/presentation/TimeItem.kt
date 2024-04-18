package com.trodar.preach.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trodar.common.constants.PreachConstant.SIXTY_MIN
import com.trodar.common.recources.K
import com.trodar.preach.R
import com.trodar.preach.domain.PreachResource
import com.trodar.domain.preach.entity.PreachCountItem
import com.trodar.utils.RepeatIconButton
import kotlinx.coroutines.delay

@SuppressLint("SimpleDateFormat")
@Composable
fun TimeItem(
    status: PreachResource.Status,
    data: PreachCountItem,
    allMinutes: PreachCountItem,
    enabled: Boolean,
    onPlusClick: (PreachCountItem, count: Int) -> Unit,
    onMinusClick: (PreachCountItem, count: Int) -> Unit,
    onUpdateTime: () -> Unit,
) {
    val delayTime = 500L
    var colon by remember {
        mutableStateOf(":")
    }
    if (status == PreachResource.Status.START) {
        LaunchedEffect(key1 = colon) {
            delay(delayTime)
            colon = if (colon == ":") " " else ":"
            onUpdateTime()
        }
    }
    Row(
        modifier = Modifier.semantics { testTag = K.PreachTag.timeRow }.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RepeatIconButton(
            modifier = Modifier
                .size(52.dp)
                .semantics { testTag = K.PreachTag.timeButtonMinus },
            onClick = { if (allMinutes.count > 0) onMinusClick(data, -1) },
            iconId = R.drawable.ic_remove_circle,
            contentDescription = "minus",
            enabled = enabled
        )

        val time =
            String.format("%d", allMinutes.count / SIXTY_MIN) + colon + String.format(
                "%02d",
                allMinutes.count % SIXTY_MIN
            )
        Box {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .semantics { testTag = K.PreachTag.timeTextTime },
                text = time,
                fontSize = 64.sp,
            )
        }

        RepeatIconButton(
            modifier = Modifier
                .size(52.dp)
                .semantics { testTag = K.PreachTag.timeButtonPlus },
            onClick = { onPlusClick(data, 1) },
            iconId = R.drawable.ic_add_circle,
            contentDescription = "add",
            enabled = enabled
        )
    }
}
