package io.github.alemazzo.sushime.ui.screens.login.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@ExperimentalMaterial3Api
@Composable
fun PasswordField(
    password: String,
    onChange: (String) -> Unit = {},
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }

    val description = if (passwordVisible) "Hide password" else "Show password"

    val (image, visualTransformation) =
        if (passwordVisible)
            Pair(Icons.Filled.VisibilityOff, VisualTransformation.None)
        else
            Pair(Icons.Filled.Visibility, PasswordVisualTransformation())

    TextField(
        value = password,
        onValueChange = onChange,
        label = { Text("Password") },
        singleLine = true,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        }
    )
}

@Preview("PasswordField")
@ExperimentalMaterial3Api
@Composable
fun PasswordFieldPreview() {
    var password by remember {
        mutableStateOf("")
    }
    PasswordField(password) { password = it }
}
