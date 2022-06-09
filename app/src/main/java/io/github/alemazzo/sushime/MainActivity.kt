package io.github.alemazzo.sushime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import io.github.alemazzo.sushime.ui.navigation.AppNavigator
import io.github.alemazzo.sushime.ui.navigation.routing.SplashRoute
import io.github.alemazzo.sushime.ui.paging.App

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App { navController, padding, _ ->
                AppNavigator(
                    navController = navController,
                    padding = padding,
                    startingRoute = SplashRoute
                )
            }
        }
    }
}
