package io.github.alemazzo.sushime.ui.screens.login.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoginButton(email: String, password: String, onLogin: () -> Unit) {
    Button(
        onClick = {
            onLogin()
        },
        modifier = Modifier.padding(top = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray,
            contentColor = Color.White
        )
    ) {
        Text(text = "Login")
    }
}
