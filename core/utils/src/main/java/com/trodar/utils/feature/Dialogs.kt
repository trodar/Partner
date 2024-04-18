package com.trodar.utils.feature

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import com.trodar.utils.R

@Composable
fun alertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: Painter? = null,
): Boolean {
    val result = remember {
        mutableStateOf(false)
    }
    AlertDialog(
        icon = {
            if (icon != null)
                Icon(icon, contentDescription = "Delete Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
            result.value = false
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(id = com.trodar.theme.R.string.yes))
                result.value = true
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(id = com.trodar.theme.R.string.no))
            }
        }
    )
    return result.value
}