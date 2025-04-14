package pt.isel.courtandgo.frontend.authentication.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var authReady by remember { mutableStateOf(false) }

    val isLoggingIn by viewModel.isLoggingIn.collectAsState()
    val loginError by viewModel.loginError.collectAsState()


    LaunchedEffect(Unit) {
        GoogleAuthProvider.create(
            credentials = GoogleAuthCredentials(
                serverId = "1fh5i2j79qsdbqihk2lmo0q6q6"
            )
        )
        authReady = true
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onNavigateBack) {
                Text("← Voltar")
            }
            Text("Court&Go", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(24.dp))

            Text("Entrar na sua conta", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("email@domain.com") },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("palavra-passe") },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.login(email, password) {
                        onLoginSuccess()
                    }
                },
                enabled = email.isNotBlank() && password.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Entrar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(modifier = Modifier.weight(1f))
                Text("  ou  ", style = MaterialTheme.typography.caption)
                Divider(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (authReady) {
                GoogleButtonUiContainer(
                    onGoogleSignInResult = { googleUser ->
                        val tokenId = googleUser?.idToken
                        if (tokenId != null) {
                            viewModel.loginWithGoogle(tokenId) {
                                onLoginSuccess()
                            }
                        } else {
                            // pode mostrar erro local
                        }
                    }
                ) {
                    GoogleSignInButton(
                        onClick = { this.onClick() },
                        modifier = Modifier.fillMaxWidth(),
                        text = "Entrar com Google"
                    )
                }
            }

            if (!loginError.isNullOrEmpty()) {
                Text(
                    text = loginError!!,
                    color = Color.Red,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

        }
    }
}
