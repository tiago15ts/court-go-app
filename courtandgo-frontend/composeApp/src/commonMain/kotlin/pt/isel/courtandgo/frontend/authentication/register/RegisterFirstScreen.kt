package pt.isel.courtandgo.frontend.authentication.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import pt.isel.courtandgo.frontend.authentication.AuthConstants
import pt.isel.courtandgo.frontend.authentication.AuthViewModel
import pt.isel.courtandgo.frontend.authentication.isValidEmail

@Composable
fun RegisterFirstScreen(
    viewModel: AuthViewModel,
    onContinueWithEmail: (String) -> Unit,
    onGoogleRegister: (String) -> Unit,
    onAlreadyHaveAccount: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var authReady by remember { mutableStateOf(false) }
    val emailValid = isValidEmail(email)
    var emailTouched by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        GoogleAuthProvider.create(
            credentials = GoogleAuthCredentials(serverId = AuthConstants.GOOGLE_SERVER_ID)
        )
        authReady = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Court&Go\uD83C\uDFBE", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Text("Crie a sua conta", style = MaterialTheme.typography.titleMedium)
        Text("Escolha um email para se registar.", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailTouched = true
            },
            placeholder = { Text("email@domain.com") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailTouched && !emailValid,
            label = { Text("Email") },
            singleLine = true
        )

        if (emailTouched && !emailValid) {
            Text(
                text = "Insira um email válido.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (emailValid) onContinueWithEmail(email)
            },
            enabled = emailValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Continuar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(modifier = Modifier.weight(1f))
            Text("  ou  ", style = MaterialTheme.typography.bodySmall)
            Divider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (authReady) {
            GoogleButtonUiContainer(
                onGoogleSignInResult = { googleUser ->
                    if (googleUser != null) {
                        viewModel.authenticateWithGoogle(
                            tokenId = googleUser.idToken,
                            name = googleUser.displayName,
                            email = googleUser.email ?: "Atualize o seu email"
                        ) {
                            onGoogleRegister(googleUser.idToken)
                            println(googleUser.idToken)
                        }
                    } else {
                        viewModel.setError("Falha ao registar-se com a conta Google.")
                    }
                }
            ) {
                GoogleSignInButton(
                    onClick = { this.onClick() },
                    modifier = Modifier.fillMaxWidth(),
                    text = "Registar-se com Google"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Já tem conta? Entrar",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onAlreadyHaveAccount() }
        )
    }
}
