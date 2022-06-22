package io.github.alemazzo.sushime.ui.screens.orders

import android.app.Application
import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.BottomBars
import io.github.alemazzo.sushime.model.database.AppDatabase
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.utils.CenteredColumn
import io.github.alemazzo.sushime.utils.getViewModel

class OrdersViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)

    val categorieDao = db.categorieDao()
    val piattiDao = db.piattiDao()

}

@ExperimentalMaterial3Api
object OrdersScreen : Screen() {

    @Composable
    override fun BottomBar(navigator: NavHostController, currentRoute: Route) {
        BottomBars.NavigateBottomBar.Get(currentRoute = currentRoute, navigator = navigator)
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        val ordersViewModel: OrdersViewModel = getViewModel()
        val categorie by ordersViewModel.categorieDao.getAll().observeAsState()


        CenteredColumn(modifier = Modifier.padding(paddingValues)) {
            categorie?.forEach {
                Text(text = "Categoria: ID = ${it.id}, NAME = ${it.nome}")
            }
        }
    }
}
