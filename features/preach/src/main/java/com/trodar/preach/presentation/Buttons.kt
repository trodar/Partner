package com.trodar.preach.presentation

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.trodar.common.entity.PreachItemTestTag
import com.trodar.preach.R

@Composable
fun MinusButton(
    modifier: Modifier,
    enabled: Boolean = true,
    preachItemTesTag: PreachItemTestTag,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier
            .semantics { testTag = preachItemTesTag.minusButton },
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Icon(
            modifier = modifier,
            painter = painterResource(id = R.drawable.ic_remove_circle),
            contentDescription = "minus",
        )
    }
}

@Composable
fun PlusButton(
    modifier: Modifier,
    enabled: Boolean = true,
    preachItemTesTag: PreachItemTestTag,
    onClick: () -> Unit,
) {
    IconButton(

        modifier = modifier
            .semantics { testTag = preachItemTesTag.plusButton },
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Icon(
            modifier = modifier,
            painter = painterResource(id = R.drawable.ic_add_circle),
            contentDescription = "add"
        )
    }
}