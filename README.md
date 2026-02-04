# Marquee
A simple marquee for Android [Jetpack Compose](https://developer.android.com/compose) that is LTR / RTL aware, with scrolling that only occurs when the content overflows its container. An interactive version is also provided for touch and fling gestures.

## Screenshots
<img src="/art/screenshot-default.gif" height=600 alt="Screenshot"> <img src="/art/screenshot-fling.gif" height=600 alt="Screenshot">

## Getting Started
The latest build is available at Maven Central (a default repository) and also [JitPack](https://jitpack.io/#com.unary/marquee).  Simply add the the library dependency to your app `build.gradle.kts`.
```
dependencies {
    // ...
    implementation("com.unary:marquee:1.0.0")
}
```
## Usage
Although typically used for `Text` any Composable content can be provided. `InteractiveMarquee` should be used when basic gestures are needed.
```
Marquee {
    Text(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = "This space for rent...",
        fontSize = 96.sp,
        maxLines = 1
    )
}
```
## Customization
`Marquee` and `InteractiveMarquee` share the same API. State can be hoisted with `rememberMarqueeState` for access to the current offset and pause values.
```
val state = rememberMarqueeState(initialOffset = 100f)
var isPaused by rememberSaveable { mutableStateOf(value = false) }

Marquee(
    modifier = Modifier.statusBarsPadding(),
    state = state,
    scrollEnabled = !isPaused,
    speed = 200.dp
) {
    // ...
}
```
## Important
Marquee composes the content multiple times in order to create a seamless loop. Avoid relying on side effects or local state that assume the content is composed only once. Such effects should be hoisted.
## License
This project is licensed under the Apache License 2.0. You may use, distribute, and modify this software under the terms of the license.
