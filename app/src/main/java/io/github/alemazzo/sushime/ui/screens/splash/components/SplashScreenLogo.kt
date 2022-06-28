package io.github.alemazzo.sushime.ui.screens.splash.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.alemazzo.sushime.R

@Composable
fun SplashScreenLogo() {
    val scale = remember {
        Animatable(0.7f)
    }
    SplashScreenAnimation(scale)
    Image(
        painter = painterResource(id = R.mipmap.icon_launcher),
        contentDescription = stringResource(id = R.string.logo_description),
        modifier = Modifier
            .size(150.dp)
            .scale(scale.value),
        //tint = contentColorFor(MaterialTheme.colorScheme.primary)
    )
}
