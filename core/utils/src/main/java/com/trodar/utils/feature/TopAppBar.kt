package com.trodar.utils.feature

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = com.trodar.theme.R.string.app_name),
    scrollBehavior: TopAppBarScrollBehavior?,
    navigationIconContent: @Composable () -> Unit = { },
    actions: @Composable RowScope.() -> Unit = { },
//    openDrawer: () -> Unit = { },
) {
    val colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        scrolledContainerColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
    )
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge
            )
        },
        colors = colors,
        navigationIcon = navigationIconContent,
        actions = actions,
        scrollBehavior = scrollBehavior,
        modifier = modifier,

        )
}