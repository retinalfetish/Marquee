package com.unary.marquee

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.unary.marquee.internal.LayoutContent
import kotlinx.coroutines.isActive

/**
 * Displays horizontally scrolling content in a continuous loop.
 *
 * Scrolls its content when the measured width exceeds the container width. It respects
 * layout direction and pauses scrolling based on the provided state.
 *
 * @param modifier Modifier for the container.
 * @param state State to hold the current offset.
 * @param scrollEnabled Whether scrolling is enabled.
 * @param speed Scroll speed in dp per second.
 * @param content Composable content to display.
 */
@Composable
fun Marquee(
    modifier: Modifier = Modifier,
    state: MarqueeState = rememberMarqueeState(),
    scrollEnabled: Boolean = MarqueeDefaults.scrollEnabled,
    speed: Dp = MarqueeDefaults.speed,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    var containerWidth by remember { mutableIntStateOf(value = 0) }

    val isScrollNeeded by remember {
        derivedStateOf { state.contentWidth > containerWidth }
    }

    val scrollDirection = if (layoutDirection == LayoutDirection.Ltr) -1 else 1

    val speedPxPerSecond = remember(key1 = speed, key2 = density) {
        with(receiver = density) { speed.toPx() }
    }

    LaunchedEffect(
        state,
        scrollEnabled,
        isScrollNeeded,
        scrollDirection,
        speedPxPerSecond
    ) {
        if (!scrollEnabled || !isScrollNeeded) return@LaunchedEffect

        var lastTime = withFrameNanos { it }

        while (isActive) {
            val now = withFrameNanos { it }
            val deltaSeconds = (now - lastTime) / 1_000_000_000f
            lastTime = now

            if (!state.isPaused) {
                state.offset += scrollDirection * speedPxPerSecond * deltaSeconds
            }
        }
    }

    Box(
        modifier = modifier
            .onSizeChanged { containerWidth = it.width }
            .clipToBounds()
    ) {
        LayoutContent(
            state = state,
            isScrollNeeded = isScrollNeeded,
            onMeasured = { state.contentWidth = it },
            content = content
        )
    }
}