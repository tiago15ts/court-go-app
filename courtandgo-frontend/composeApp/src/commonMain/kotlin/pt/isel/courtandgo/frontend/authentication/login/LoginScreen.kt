package pt.isel.courtandgo.frontend.authentication.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
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
import pt.isel.courtandgo.frontend.authentication.AuthConstants
import pt.isel.courtandgo.frontend.authentication.AuthUiState
import pt.isel.courtandgo.frontend.authentication.AuthViewModel
import pt.isel.courtandgo.frontend.authentication.isValidEmail
import pt.isel.courtandgo.frontend.components.SnackbarError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    val emailValid = isValidEmail(email)
    var password by remember { mutableStateOf("") }
    var authReady by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()
    val isLoggingIn = uiState is AuthUiState.Loading
    val loginError = (uiState as? AuthUiState.Error)?.message

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(loginError) {
        if (!loginError.isNullOrEmpty()) {
            snackbarHostState.showSnackbar((uiState as AuthUiState.Error).message)
            viewModel.clearError()
        }
    }

    LaunchedEffect(Unit) {
        GoogleAuthProvider.create(
            credentials = GoogleAuthCredentials(
                serverId = AuthConstants.GOOGLE_SERVER_ID
            )
        )
        authReady = true
    }

    Scaffold(
        snackbarHost = { SnackbarError(snackbarHostState) },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(40.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Voltar",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Court&Go", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(24.dp))

                Text("Entrar na sua conta", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("email@domain.com") },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    isError = email.isNotBlank() && !emailValid,
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
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
                        viewModel.loginWithEmail(email, password) {
                            onLoginSuccess()
                        }
                    },
                    enabled = emailValid && password.isNotBlank() && !isLoggingIn,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    if (isLoggingIn) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Entrar")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f))
                    Text("  ou  ", style = MaterialTheme.typography.bodySmall)
                    HorizontalDivider(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (authReady) {
                    GoogleButtonUiContainer(
                        onGoogleSignInResult = { googleUser ->
                            if (googleUser != null) {
                                viewModel.authenticateWithGoogle(
                                    tokenId = googleUser.idToken,
                                    name = googleUser.displayName,
                                    email = googleUser.email ?: "Atualize o seu email",
                                ) {
                                    onLoginSuccess()
                                }
                            }  else {
                                viewModel.setError("Falha ao autenticar-se com a conta Google.")
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
                        text = loginError,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
