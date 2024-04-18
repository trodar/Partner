package com.trodar.preach.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trodar.common.entity.CountEntity
import com.trodar.common.entity.PreachItemTestTag
import com.trodar.common.recources.K
import com.trodar.domain.preach.entity.PreachCountItem
import com.trodar.theme.R
import com.trodar.theme.themes.JWPartnerTheme

@Composable
fun PreachItem(
    data: PreachCountItem,
    enabled: Boolean = true,
    preachItemTesTag: PreachItemTestTag,
    onPlusClick: (PreachCountItem, count: Int) -> Unit,
    onMinusClick: (PreachCountItem, count: Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .width(340.dp)
            .semantics { testTag = preachItemTesTag.rowContainer },
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            text = stringResource(data.textId),
            fontSize = 28.sp
        )

        MinusButton(
            modifier = Modifier.size(38.dp),
            enabled = enabled,
            preachItemTesTag = preachItemTesTag,
        ) {
            onMinusClick(data, -1)
        }

        Text(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .semantics { testTag = preachItemTesTag.textNumber },
            text = data.count.toString(),
            fontSize = 42.sp
        )

        PlusButton(
            modifier = Modifier.size(38.dp),
            enabled = enabled,
            preachItemTesTag = preachItemTesTag
        ) {
            onPlusClick(data, 1)
        }
    }
}

@Preview("preach item", showBackground = true, device = Devices.NEXUS_7_2013)
@Preview(
    "preach item (ru)",
    locale = "ru-rRU",
    showBackground = true,
    device = Devices.NEXUS_7_2013
)
@Composable
fun PreviewPreachItemPublication() {
    val preachCountItem = PreachCountItem(
        CountEntity.PreachType.PUBLICATION,
        0,
        R.string.preach_publication
    )
    JWPartnerTheme {
        PreachItem(preachCountItem, true, K.PreachTag.preachItemList[0], { _, _ -> }, { _, _ -> })
    }
}

@Preview("preach item", showBackground = true, device = Devices.NEXUS_7_2013)
@Preview(
    "preach item (ru)",
    locale = "ru-rRU",
    showBackground = true,
    device = Devices.NEXUS_7_2013
)
@Composable
fun PreviewPreachItemVideo() {
    val preachCountItem =
        PreachCountItem(CountEntity.PreachType.VIDEO, 0, R.string.preach_video)
    JWPartnerTheme {
        PreachItem(preachCountItem, true, K.PreachTag.preachItemList[0], { _, _ -> }, { _, _ -> })
    }
}

@Preview("preach item", showBackground = true, device = Devices.NEXUS_7_2013)
@Preview(
    "preach item (ru)",
    locale = "ru-rRU",
    showBackground = true,
    device = Devices.NEXUS_7_2013
)
@Composable
fun PreviewPreachItemReturn() {
    val preachCountItem =
        PreachCountItem(CountEntity.PreachType.RETURN, 0, R.string.preach_return)
    JWPartnerTheme {
        PreachItem(preachCountItem, true, K.PreachTag.preachItemList[0], { _, _ -> }, { _, _ -> })
    }
}

@Preview("preach item", showBackground = true, device = Devices.NEXUS_7_2013)
@Preview(
    "preach item (ru)",
    locale = "ru-rRU",
    showBackground = true,
    device = Devices.NEXUS_7_2013
)
@Composable
fun PreviewPreachItemStudy() {
    val preachCountItem =
        PreachCountItem(CountEntity.PreachType.STUDY, 0, R.string.preach_study)
    JWPartnerTheme {
        PreachItem(preachCountItem, true, K.PreachTag.preachItemList[0], { _, _ -> }, { _, _ -> })
    }
}
