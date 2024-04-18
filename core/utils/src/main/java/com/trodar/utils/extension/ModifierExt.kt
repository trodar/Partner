package com.trodar.utils.extension

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Modifier.repeatingClickable(
    interactionSource: InteractionSource,
    enabled: Boolean,
    maxDelayMillis: Long = 500,
    minDelayMillis: Long = 10,
    delayDecayFactor: Float = .08f,
    onClick: () -> Unit
): Modifier = composed {

    val currentClickListener by rememberUpdatedState(onClick)

    pointerInput(interactionSource, enabled) {
        awaitEachGesture {
            val down = awaitFirstDown(requireUnconsumed = false)
            val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
            val heldButtonJob = scope.launch {
                var currentDelayMillis = maxDelayMillis
                while (enabled && down.pressed) {
                    currentClickListener()
                    delay(currentDelayMillis)
                    val nextMillis =
                        currentDelayMillis - (currentDelayMillis * delayDecayFactor)
                    currentDelayMillis = nextMillis.toLong().coerceAtLeast(minDelayMillis)
                }
            }
            waitForUpOrCancellation()
            heldButtonJob.cancel()
        }
    }
}