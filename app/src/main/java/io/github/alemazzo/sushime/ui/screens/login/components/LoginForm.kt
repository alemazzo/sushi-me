package io.github.alemazzo.sushime.ui.screens.login.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@ExperimentalMaterial3Api
@Composable
fun LoginForm(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    errorMessage: String?,
    onLogin: () -> Unit
) {
    Scaffold { it ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            modifier = Modifier
                .fillMaxSize()
                .padding(it)

            //.background(MaterialTheme.colors.secondary),
        ) {
            LoginTitle()
            RoundedTextField("Email", email, onEmailChange)
            Spacer(modifier = Modifier.height(10.dp))
            PasswordField(password, onPasswordChange)
            errorMessage?.run {
                Text(
                    text = this,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    ),
                    color = Color.Red
                )
            }
            LoginButton(email = email, password = password, onLogin = onLogin)
        }
    }
}
