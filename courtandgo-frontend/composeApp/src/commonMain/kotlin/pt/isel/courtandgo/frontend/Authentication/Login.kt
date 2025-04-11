package pt.isel.courtandgo.frontend.authentication


import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(authManager: AuthManager) {
    var isLoggingIn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bem-vindo ao Court & Go!", style = MaterialTheme.typography.h3)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                isLoggingIn = true
                authManager.login()
            },
            enabled = !isLoggingIn
        ) {
            Text("Entrar com Google ou Email")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoggingIn) {
            CircularProgressIndicator()
        }
    }
}

