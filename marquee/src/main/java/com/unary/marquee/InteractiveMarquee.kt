package com.unary.marquee

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp

/**
 * Displays a marquee that supports user interaction.
 *
 * Scrolling is paused while the content is pressed or dragged and resumes when the
 * interaction ends. It respects layout direction and scrolling state.
 *
 * @param modifier Modifier for the container.
 * @param state State to hold the current offset.
 * @param scrollEnabled Whether scrolling is enabled.
 * @param speed Scroll speed in dp per second.
 * @param content Composable content to display.
 */
@Composable
fun InteractiveMarquee(
    modifier: Modifier = Modifier,
    state: MarqueeState = rememberMarqueeState(),
    scrollEnabled: Boolean = MarqueeDefaults.scrollEnabled,
    speed: Dp = MarqueeDefaults.speed,
    content: @Composable () -> Unit
) {
    val scrollableState = rememberScrollableState { delta ->
        state.offset += delta
        delta
    }

    var isPressed by remember { mutableStateOf(value = false) }

    LaunchedEffect(
        key1 = isPressed,
        key2 = scrollableState.isScrollInProgress
    ) {
        state.isPaused = isPressed || scrollableState.isScrollInProgress
    }

    Marquee(
        modifier = modifier
            .pointerInput(key1 = Unit) {
                awaitEachGesture {
                    awaitFirstDown(requireUnconsumed = false)
                    isPressed = true
                    waitForUpOrCancellation()
                    isPressed = false
                }
            }
            .scrollable(
                state = scrollableState,
                orientation = Orientation.Horizontal,
                enabled = scrollEnabled
            ),
        state = state,
        scrollEnabled = scrollEnabled,
        speed = speed,
        content = content
    )
}