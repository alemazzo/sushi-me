package io.github.alemazzo.sushime.ui.screens.splash

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.navigation.routing.RoutePreview
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.splash.components.LoadDataAndChangeScreenAtTheEnd
import io.github.alemazzo.sushime.ui.screens.splash.components.SplashScreenContent
import io.github.alemazzo.sushime.ui.screens.splash.viewmodel.SplashViewModel
import io.github.alemazzo.sushime.utils.getViewModel

@ExperimentalMaterial3Api
object SplashScreen : Screen() {
    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        val splashViewModel: SplashViewModel = getViewModel()
        LoadDataAndChangeScreenAtTheEnd(splashViewModel, navigator)
        SplashScreenContent(paddingValues)
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun SplashScreenPreview() {
    RoutePreview(route = Routes.SplashRoute)
}
