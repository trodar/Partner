package com.example.notepad.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notepad.di.notepadEditViewModelCreator
import com.trodar.theme.themes.JWPartnerTheme
import com.trodar.utils.feature.BackButton
import com.trodar.utils.feature.SaveButton
import com.trodar.utils.feature.TopAppBar
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay

@Composable
fun NotepadEdit(
    id: Int,
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    val notepadEditViewModel: NotepadEditViewModel = notepadEditViewModelCreator(id)

    val notepadState = notepadEditViewModel.notepadState.collectAsState().value

    NotepadEdit(
        paddingValues = paddingValues,
        notepadState = notepadState,
        navController = navController,
        onSave = { notepadEditViewModel.save() },
        onTextChange = { text ->
            notepadEditViewModel.changeText(text)
        }
    )

}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotepadEdit(
    paddingValues: PaddingValues,
    notepadState: NotepadEditState,
    navController: NavHostController,
    onSave: () -> Unit,
    onTextChange: (TextFieldValue) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val delayTime = 500L

    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = null,
                navigationIconContent = {
                    BackButton {
                        navController.popBackStack()
                        if (notepadState.edited)
                            onSave()

                    }
                },
                actions = {
                    SaveButton(enabled = notepadState.edited) {
                        if (notepadState.edited)
                            onSave()
                    }
                }
            )
        }

    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
                .padding(bottom = if (!WindowInsets.isImeVisible) paddingValues.calculateBottomPadding() else 0.dp)
                .imePadding()

        ) {

            TextField(
                modifier = Modifier
                    .fillMaxSize()
                    .focusRequester(focusRequester),
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                value = notepadState.textFieldValue,
                onValueChange = { onTextChange(it) },

                )
        }
    }

    LaunchedEffect(notepadState.textFieldValue) {
        delay(delayTime)
        onSave()
    }
    if (notepadState.textFieldValue.text.isBlank())
        LaunchedEffect(focusRequester) {
            awaitFrame()
            focusRequester.requestFocus()
        }
}

@Preview("Report screen")
@Preview("Report screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NotepadEditPreview() {
    val context = LocalContext.current

    JWPartnerTheme {
        NotepadEdit(
            notepadState = NotepadEditState(
                textFieldValue = TextFieldValue(text = """
                    Jetpack Compose is Android’s recommended modern toolkit for building native UI. 
                    It simplifies and accelerates UI development on Android. 
                    Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs.
                """.trimIndent())
            ),
            navController = NavHostController(context),
            paddingValues = PaddingValues(),
            onSave = { },
            onTextChange = { _ -> }
        )
    }
}

@Preview("Report screen")
@Preview("Report screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NotepadEditedPreview() {
    val context = LocalContext.current

    JWPartnerTheme {
        NotepadEdit(
            notepadState = NotepadEditState(
                edited = true,
                textFieldValue = TextFieldValue(
                    text = """
                    Jetpack Compose is Android’s recommended modern toolkit for building native UI. 
                    It simplifies and accelerates UI development on Android. 
                    Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs.
                """.trimIndent())
            ),
            navController = NavHostController(context),
            paddingValues = PaddingValues(),
            onSave = { },
            onTextChange = { _ -> }
        )
    }
}