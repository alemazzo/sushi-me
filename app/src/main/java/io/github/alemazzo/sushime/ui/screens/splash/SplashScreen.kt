package io.github.alemazzo.sushime.ui.screens.splash

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.ui.paging.App
import io.github.alemazzo.sushime.ui.screens.splash.components.LoadDataAndChangeScreenAtTheEnd
import io.github.alemazzo.sushime.ui.screens.splash.components.SplashScreenContent
import io.github.alemazzo.sushime.ui.screens.splash.viewmodel.SplashViewModel
import io.github.alemazzo.sushime.utils.getViewModel

@ExperimentalMaterial3Api
@Composable
fun SplashScreen(
    navController: NavHostController,
    padding: PaddingValues,
) {
    val splashViewModel: SplashViewModel = getViewModel()
    LoadDataAndChangeScreenAtTheEnd(splashViewModel, navController)
    SplashScreenContent(padding)
}


@ExperimentalMaterial3Api
@Preview(
    "SplashScreen: Light Theme",
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun SplashScreenPreview() {
    App { navController, padding, _ ->
        SplashScreen(navController, padding)
    }
}
