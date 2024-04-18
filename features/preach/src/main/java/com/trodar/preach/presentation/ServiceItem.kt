package com.trodar.preach.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trodar.common.recources.K
import com.trodar.preach.domain.PreachResource
import com.trodar.theme.R
import com.trodar.theme.colors.textFieldColors
import com.trodar.theme.colors.outlineButtonColor
import com.trodar.theme.themes.JWPartnerTheme

@Composable
fun ServiceItem(
    status: PreachResource.Status,
    description: String,
    onStartService: (Boolean) -> Unit,
    onPauseService: () -> Unit,
    onStopService: () -> Unit,
    onCancelService: () -> Unit,
    onSaveClick: () -> Unit,
    onValueChange: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .semantics { testTag = K.PreachTag.serviceRow },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            if (status == PreachResource.Status.DEFAULT) {
                val context = LocalContext.current
                OutlinedButton(
                    onClick = { onStartService(context.packageName.contains("test")) },
                    colors = outlineButtonColor(),
                    modifier = Modifier.semantics { testTag = K.PreachTag.serviceButtonStart }
                ) {
                    Text(
                        text = stringResource(id = R.string.preach_start),
                        fontSize = 24.sp,
                    )
                }
            }
            if (status == PreachResource.Status.START || status == PreachResource.Status.PAUSE)
                OutlinedButton(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .semantics { testTag = K.PreachTag.serviceButtonPause },
                    onClick = { onPauseService() },
                    colors = outlineButtonColor(),
                ) {
                    Text(
                        text = if (status == PreachResource.Status.PAUSE) stringResource(id = R.string.preach_resume)
                        else stringResource(id = R.string.preach_stop),
                        fontSize = 24.sp,
                    )
                }
            if (status == PreachResource.Status.START || status == PreachResource.Status.PAUSE)
                OutlinedButton(
                    modifier = Modifier.semantics { testTag = K.PreachTag.serviceButtonFinish },
                    onClick = { onStopService() },
                    colors = outlineButtonColor(),
                ) {
                    Text(
                        text = stringResource(id = R.string.preach_finish),
                        fontSize = 24.sp,
                    )
                }
            if (status == PreachResource.Status.FINISH)
                OutlinedButton(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .semantics { testTag = K.PreachTag.serviceButtonCancel },
                    onClick = { onCancelService() },
                    colors = outlineButtonColor(),
                ) {
                    Text(
                        text = stringResource(id = R.string.preach_cancel),
                        fontSize = 24.sp,
                    )
                }
            if (status == PreachResource.Status.FINISH)
                OutlinedButton(
                    modifier = Modifier.semantics { testTag = K.PreachTag.serviceButtonSave },
                    onClick = { onSaveClick() },
                    colors = outlineButtonColor(),
                ) {
                    Text(
                        text = stringResource(id = R.string.preach_save),
                        fontSize = 24.sp,
                    )
                }
        }
        if (status == PreachResource.Status.FINISH)
            Row(
                modifier = Modifier
                    .background(Color.Transparent),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val focusRequester = FocusRequester()
                TextField(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .focusRequester(focusRequester)
                        .semantics { testTag = K.PreachTag.serviceEditTestDescription },
                    colors = textFieldColors(),
                    textStyle = TextStyle.Default.copy(fontSize = 24.sp),

                    value = description,
                    onValueChange = { onValueChange(it) },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.preach_description),
                        )
                    })
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            }
    }
}

@Preview("service item",showBackground = true,device = Devices.NEXUS_7_2013)
@Preview("service item (ru)", locale = "ru-rRU",showBackground = true,device = Devices.NEXUS_7_2013)
@Composable
fun PreviewServiceItemStart(status: PreachResource.Status = PreachResource.Status.START) {
    JWPartnerTheme {
        ServiceItem(status, "write here", {}, {}, {}, {}, {}, {})
    }
}

@Preview("service item",showBackground = true,device = Devices.NEXUS_7_2013)
@Preview("service item (ru)", locale = "ru-rRU",showBackground = true,device = Devices.NEXUS_7_2013)
@Composable
fun PreviewServiceItemFinish(status: PreachResource.Status = PreachResource.Status.FINISH) {
    JWPartnerTheme {
        ServiceItem(status, stringResource(id = R.string.preach_description), {}, {}, {}, {}, {}, {})
    }
}

@Preview("service item",showBackground = true,device = Devices.NEXUS_7_2013)
@Preview("service item (ru)", locale = "ru-rRU",showBackground = true,device = Devices.NEXUS_7_2013)
@Composable
fun PreviewServiceItemDefault(status: PreachResource.Status = PreachResource.Status.DEFAULT) {
    JWPartnerTheme {
        ServiceItem(status, stringResource(id = R.string.preach_description), {}, {}, {}, {}, {}, {})
    }
}

@Preview("service item",showBackground = true,device = Devices.NEXUS_7_2013)
@Preview("service item (ru)", locale = "ru-rRU",showBackground = true,device = Devices.NEXUS_7_2013)
@Composable
fun PreviewServiceItemPause(status: PreachResource.Status = PreachResource.Status.PAUSE) {
    JWPartnerTheme {
        ServiceItem(status, stringResource(id = R.string.preach_description), {}, {}, {}, {}, {}, {})
    }
}
