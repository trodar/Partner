package com.trodar.report.domain.backup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.trodar.theme.R
import com.trodar.utils.feature.showShortToast

@Composable
fun rememberExportBackupActivity(context: Context): ManagedActivityResultLauncher<Intent, ActivityResult>  {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            if (it.resultCode == Activity.RESULT_OK) {
                showShortToast(context, context.getString(R.string.backup_created))
            }
        }
    )
}

@Composable
fun rememberImportBackupActivity(onSuccess: (Uri) -> Unit): ManagedActivityResultLauncher<Array<String>, Uri?> {
    return rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument(),
        onResult = {
            if (it != null) {
                onSuccess(it)
            }
        }
    )
}