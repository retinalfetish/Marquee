package com.unary.marquee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unary.marquee.ui.theme.MarqueeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarqueeTheme {
                var pause by rememberSaveable { mutableStateOf(value = false) }

                // Try InteractiveMarquee() for basic gestures

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Marquee(
                        scrollEnabled = !pause,
                        speed = 200.dp
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            text = "This space for rent...",
                            fontSize = 96.sp,
                            maxLines = 1
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(all = 8.dp),
                    onClick = { pause = !pause }
                ) {
                    Text(text = if (pause) "Resume" else "Pause")
                }
            }
        }
    }
}