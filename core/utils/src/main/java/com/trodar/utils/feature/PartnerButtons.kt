package com.trodar.utils.feature

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.trodar.theme.colors.iconButtonColor

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = stringResource(com.trodar.theme.R.string.back),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun SendButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            imageVector = Icons.Rounded.Send,
            contentDescription = stringResource(com.trodar.theme.R.string.send),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun SaveButton(enabled: Boolean = true, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        colors = iconButtonColor()
    ) {
        Icon(
            painter = painterResource(id = com.trodar.theme.R.drawable.save_24),
            contentDescription = stringResource(com.trodar.theme.R.string.preach_save),
        )
    }
}

@Composable
fun ExportButton(
    context: Context,
    onExportClick: () -> Unit,
    onImportClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .width(48.dp)

    ) {
        IconButton(onClick = { menuExpanded = !menuExpanded }) {
            Icon(
                painter = painterResource(id = com.trodar.theme.R.drawable.cloud_sync_24),
                contentDescription = "More"
            )
        }

        DropdownMenu(
            modifier = Modifier.background(MaterialTheme.colorScheme.tertiaryContainer),
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = context.getString(com.trodar.theme.R.string.export_data),
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = com.trodar.theme.R.drawable.cloud_upload_24),
                        contentDescription = context.getString(com.trodar.theme.R.string.export_data),
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                },
                onClick = {
                    menuExpanded = false
                    onExportClick()

                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        context.getString(com.trodar.theme.R.string.import_data),
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = com.trodar.theme.R.drawable.cloud_download_24),
                        contentDescription = context.getString(com.trodar.theme.R.string.export_data),
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                },
                onClick = {
                    menuExpanded = false
                    onImportClick()
                }
            )
        }
    }
}

