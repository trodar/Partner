package com.trodar.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.trodar.utils.extension.repeatingClickable

@Composable
fun RepeatIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    maxDelayMillis: Long = 250,
    minDelayMillis: Long = 10,
    delayDecayFactor: Float = .08f,
    @DrawableRes iconId: Int,
    contentDescription: String?
) {
    IconButton(
        modifier = modifier.repeatingClickable(
            interactionSource = interactionSource,
            enabled = enabled,
            maxDelayMillis = maxDelayMillis,
            minDelayMillis = minDelayMillis,
            delayDecayFactor = delayDecayFactor
        ) { onClick() },
        onClick = { },
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            painter = painterResource(id = iconId),
            contentDescription = contentDescription
        )
    }
}
