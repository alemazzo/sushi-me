package io.github.alemazzo.sushime.ui.navigation

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@ExperimentalMaterial3Api
abstract class Screen {

    @Composable
    abstract fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    )

    @Composable
    open fun BottomBar(navigator: NavHostController, currentRoute: Route) {
        
    }

    @Composable
    open fun TopBar() {
    }

    @Composable
    open fun FloatingActionButton() {
    }


    @Composable
    inline fun <reified T : ViewModel> GetViewModel() {
        viewModel<T>()
    }

}
