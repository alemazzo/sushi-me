package io.github.alemazzo.sushime.ui.screens.join

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.BottomBars
import io.github.alemazzo.sushime.Routes
import io.github.alemazzo.sushime.ui.navigation.Route
import io.github.alemazzo.sushime.ui.navigation.RoutePreview
import io.github.alemazzo.sushime.ui.navigation.Screen
import io.github.alemazzo.sushime.utils.qr.QRScanner


@ExperimentalMaterial3Api
object JoinScreen : Screen() {

    @Composable
    override fun BottomBar(navigator: NavHostController, currentRoute: Route) {
        BottomBars.NavigateBottomBar.Get(currentRoute, navigator)
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        JoinScreenContent(navigator, paddingValues)
    }

}

@Composable
fun JoinScreenContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    var code by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QRScanner(onTextChange = { code = it }, width = 300.dp, height = 300.dp)
        Text(
            text = "QRText: $code",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(32.dp)

        )

    }
}

@ExperimentalMaterial3Api
@Composable
@androidx.compose.ui.tooling.preview.Preview
fun JoinScreenPreview() {
    RoutePreview(route = Routes.JoinRoute)
}
