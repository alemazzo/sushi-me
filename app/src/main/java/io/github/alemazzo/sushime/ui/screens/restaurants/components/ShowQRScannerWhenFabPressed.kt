package io.github.alemazzo.sushime.ui.screens.restaurants.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import io.github.alemazzo.sushime.utils.qr.QRScanner

@Composable
fun ShowQRScannerWhenFabPressed(
    showPopup: Boolean,
    onEnd: () -> Unit,
    onResult: (String) -> Unit,
) {
    if (showPopup) {
        BackPressHandler {
            onEnd()
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = {
                    onEnd()
                }
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .width(350.dp)
                        .height(400.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Scan Restaurant's QR",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black)
                    QRScanner(onTextChange = {
                        if (it != "") {
                            onResult(it)
                            onEnd()
                        }
                    }, width = 300.dp, height = 300.dp)
                }
            }
        }

    }
}
