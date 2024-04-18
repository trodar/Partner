package com.trodar.utils.feature

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.trodar.theme.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeDismiss(
    onDeleteClick: () -> Unit,
    dismissContent: @Composable RowScope.() -> Unit,
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val scope = rememberCoroutineScope()
    val swipeState = rememberSwipeToDismissBoxState(

        positionalThreshold = { screenWidth.value }
    )

    SwipeToDismissBox(
        modifier = Modifier.animateContentSize(),
        enableDismissFromStartToEnd = false,
        state = swipeState,
        backgroundContent = {

            val backgroundColor by animateColorAsState(
                when (swipeState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> Color.Red.copy(alpha = 0.8f)
                    SwipeToDismissBoxValue.StartToEnd -> Color.Green.copy(alpha = 0.8f)
                    else -> MaterialTheme.colorScheme.background
                }, label = ""
            )

            // icon
            val iconImageVector = when (swipeState.targetValue) {
                SwipeToDismissBoxValue.StartToEnd -> Icons.Outlined.AddCircle
                else -> Icons.Outlined.Delete
            }

            // icon placement
            val iconAlignment = when (swipeState.targetValue) {
                SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                else -> Alignment.CenterEnd
            }

            // icon size
            val iconScale by animateFloatAsState(
                targetValue = if (swipeState.targetValue == SwipeToDismissBoxValue.Settled) 1f else 1.8f,
                label = ""
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = backgroundColor)
                    .padding(horizontal = 16.dp), // inner padding
                contentAlignment = iconAlignment
            ) {
                Icon(
                    modifier = Modifier.scale(iconScale),
                    imageVector = iconImageVector,
                    contentDescription = null,
                    tint = if (swipeState.targetValue == SwipeToDismissBoxValue.Settled) Color.Red else
                        Color.White
                )
            }
        },
        content = dismissContent
    )

    when (swipeState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            openAlertDialog.value = true
        }

        SwipeToDismissBoxValue.StartToEnd -> {
        }

        SwipeToDismissBoxValue.Settled -> {
        }
    }
    if (openAlertDialog.value)
        alertDialog(
            onDismissRequest = {
                openAlertDialog.value = false
                scope.launch { swipeState.snapTo(SwipeToDismissBoxValue.Settled) }
            },
            onConfirmation = {
                openAlertDialog.value = false
                onDeleteClick()
            },
            dialogTitle = stringResource(id = R.string.delete),
            dialogText = stringResource(id = R.string.are_you_sure),
        )

}