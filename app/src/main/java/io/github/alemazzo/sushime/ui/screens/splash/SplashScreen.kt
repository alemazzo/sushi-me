package io.github.alemazzo.sushime.ui.screens.splash

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.Routes
import io.github.alemazzo.sushime.ui.navigation.RoutePreview
import io.github.alemazzo.sushime.ui.navigation.Screen
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
@Preview(
    "SplashScreen: Light Theme",
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun SplashScreenPreview() {
    RoutePreview(route = Routes.SplashRoute)
}
