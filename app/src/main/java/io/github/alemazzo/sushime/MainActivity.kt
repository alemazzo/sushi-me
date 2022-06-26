package io.github.alemazzo.sushime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.navigation.App

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                routes = Routes.all(),
                startingRoute = Routes.SplashRoute
            )
        }
    }
}
