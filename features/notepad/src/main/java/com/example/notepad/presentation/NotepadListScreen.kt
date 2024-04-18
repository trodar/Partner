package com.example.notepad.presentation

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trodar.common.Core
import com.trodar.domain.notepad.NotepadEntity
import com.trodar.theme.themes.JWPartnerTheme
import com.trodar.utils.feature.SwipeDismiss
import com.trodar.utils.feature.TopAppBar
import com.trodar.utils.feature.getDate
import com.trodar.utils.feature.showShortToast
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotepadScreen(
    notepadListViewModel: NotepadListViewModel = hiltViewModel(),
    scrollBehavior: TopAppBarScrollBehavior,
    onNewClick: () -> Unit,
    onClick: (Int) -> Unit
) {
    if (Core.updateNode.notepadNode) {
        Core.updateNode.notepadNode = false
        notepadListViewModel.load()
    }
    val notepadState = notepadListViewModel.notepadState.collectAsState().value
    val context = LocalContext.current

    NotepadScreen(
        notepadList = notepadState.notepadList,
        topAppBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
            )
        },
        scrollBehavior = scrollBehavior,
        onClick = onClick,
        onNewClick = onNewClick,
        onDeleteItem = {
            notepadListViewModel.delete(it)
            showShortToast(
                context,
                message = context.getString(com.trodar.theme.R.string.preach_deleted)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotepadScreen(
    notepadList: List<NotepadEntity>,
    topAppBar: @Composable () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    onClick: (Int) -> Unit,
    onNewClick: () -> Unit,
    onDeleteItem: (NotepadEntity) -> Unit,
) {
    val modifier = Modifier.padding(bottom = 80.dp)
    Scaffold(
        modifier = Modifier,
        topBar = topAppBar,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNewClick,
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
                modifier = modifier,
                containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),

                ) {
                Icon(
                    imageVector = Icons.Outlined.AddCircle,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentDescription = stringResource(com.trodar.theme.R.string.new_item)
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .imePadding(),
            contentPadding = padding
        ) {
            items(notepadList, { it.id }) { item ->
                SwipeDismiss(onDeleteClick = { onDeleteItem(item) }) {
                    Column(modifier = Modifier
                        .padding(vertical = 1.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .clickable {
                            onClick(item.id)
                        }
                    ) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            text = item.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            text = getDate(item.date, LocalContext.current),
                            style = MaterialTheme.typography.titleSmall,
                            textAlign = TextAlign.End
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }
            item {
                Spacer(
                    Modifier.height(72.dp)
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    "Notepad screen",
    showBackground = true,
    device = Devices.NEXUS_6,
    apiLevel = Build.VERSION_CODES.TIRAMISU
)
@Preview(
    "Notepad screen (dark)", showBackground = true, device = Devices.NEXUS_6,
    uiMode = Configuration.UI_MODE_NIGHT_YES, apiLevel = Build.VERSION_CODES.TIRAMISU
)
@Composable
fun PreviewNotepad() {

    val list = listOf(
        NotepadEntity(1, "My first item", Date()),
        NotepadEntity(2, "Next time will good time", Date()),
        NotepadEntity(3, "Long text here, but not here. where is long text", Date()),
        NotepadEntity(4, "My four item", Date()),
        NotepadEntity(5, "My four item sdf ds sad fsa steadfastness", Date()),
    )
    JWPartnerTheme {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        NotepadScreen(
            notepadList = list,
            scrollBehavior = scrollBehavior,
            //paddingValues = PaddingValues(),
            topAppBar = {},
            onNewClick = {},
            onDeleteItem = {},
            onClick = {}
        )
    }
}







































