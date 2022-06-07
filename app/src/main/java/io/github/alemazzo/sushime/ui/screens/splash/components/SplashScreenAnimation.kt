package io.github.alemazzo.sushime.ui.screens.splash.components

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun SplashScreenAnimation(scale: Animatable<Float, AnimationVector1D>) {
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = repeatable(
                repeatMode = RepeatMode.Reverse,
                iterations = 100,
                animation = tween(
                    durationMillis = 1000
                )
            )
        )
    }
}
