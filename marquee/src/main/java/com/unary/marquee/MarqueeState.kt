package com.unary.marquee

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

/**
 * A Compose friendly state holder that manages current marquee settings.
 *
 * @param initialOffset The initial scroll offset.
 */
@Stable
class MarqueeState internal constructor(initialOffset: Float) {

    /** The measured content width */
    internal var contentWidth by mutableIntStateOf(value = 0)

    private var _offset by mutableFloatStateOf(value = initialOffset)

    /** The current scroll offset. */
    var offset: Float
        get() = _offset
        internal set(value) =
            if (contentWidth > 0) {
                _offset = ((value % contentWidth) + contentWidth) % contentWidth
            } else {
                _offset = value
            }

    /** Whether scrolling is paused. */
    var isPaused by mutableStateOf(value = false)
        internal set

    companion object {

        /** Saves and restores a [MarqueeState]. */
        val Saver: Saver<MarqueeState, Float> =
            Saver(
                save = { it.offset },
                restore = { MarqueeState(initialOffset = it) }
            )
    }
}

/**
 * Creates and remembers a [MarqueeState] that survives configuration changes.
 *
 * @param initialOffset The initial scroll offset.
 * @return The remembered [MarqueeState].
 */
@Composable
fun rememberMarqueeState(initialOffset: Float = 0f): MarqueeState =
    rememberSaveable(saver = MarqueeState.Saver) { MarqueeState(initialOffset = initialOffset) }