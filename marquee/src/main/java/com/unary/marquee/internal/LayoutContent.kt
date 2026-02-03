package com.unary.marquee.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import com.unary.marquee.MarqueeState

/**
 * Layout that measures and offsets marquee content for seamless scrolling.
 *
 * When scrolling is required, the content is repeated and translated based on the current
 * scroll offset.
 *
 * @param modifier Modifier for the container.
 * @param state State to hold the current offset.
 * @param isScrollNeeded Whether content overflows its container.
 * @param onMeasured Callback for measured content width.
 * @param content Composable content to display.
 */
@Composable
internal fun LayoutContent(
    modifier: Modifier = Modifier,
    state: MarqueeState,
    isScrollNeeded: Boolean,
    onMeasured: (Int) -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .wrapContentWidth(unbounded = true)
            .onSizeChanged { onMeasured(it.width) }
            .graphicsLayer {
                if (isScrollNeeded) {
                    translationX = state.offset - (state.contentWidth / 2)
                }
            }
    ) {
        if (isScrollNeeded) {
            repeat(times = 3) { index ->
                Box(
                    modifier = Modifier.offset {
                        IntOffset(x = (index - 1) * state.contentWidth, y = 0)
                    }
                ) {
                    content()
                }
            }
        } else {
            content()
        }
    }
}