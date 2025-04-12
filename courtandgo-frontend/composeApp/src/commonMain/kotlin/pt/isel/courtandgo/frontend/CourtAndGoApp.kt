package pt.isel.courtandgo.frontend

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import pt.isel.courtandgo.frontend.authentication.login.LoginScreen
import pt.isel.courtandgo.frontend.authentication.register.RegisterDetailsScreen
import pt.isel.courtandgo.frontend.authentication.register.RegisterFirstScreen
import pt.isel.courtandgo.frontend.home.HomeScreen

@Composable
fun CourtAndGoApp() {
    val screen = remember { mutableStateOf<Screen>(Screen.RegisterFirst) }

    MaterialTheme {
        when (val current = screen.value) {
            is Screen.RegisterFirst -> RegisterFirstScreen(
                onContinueWithEmail = { email ->
                    screen.value = Screen.RegisterDetails(email)
                },
                onGoogleRegister = { tokenId ->
                    // Podes usar o token para autenticar no backend
                    screen.value = Screen.Home
                },
                onAlreadyHaveAccount = {
                    screen.value = Screen.Login
                }
            )

            is Screen.RegisterDetails -> RegisterDetailsScreen(
                email = current.email,
                onRegister = { email, name, contact, password ->
                    // TODO: Call backend API to register user
                    screen.value = Screen.Home
                },
                onNavigateBack = {
                    screen.value = Screen.RegisterFirst
                }
            )

            is Screen.Login -> LoginScreen(
                onLoginSuccess = { email, password ->
                    screen.value = Screen.Home
                },
                onGoogleLogin = { tokenId ->
                    // dá para usar o token para autenticar no backend
                    screen.value = Screen.Home
                },
                isLoggingIn = false,
                onLoginFailure = {

                },
                onNavigateBack = {
                    screen.value = Screen.RegisterFirst
                }
            )

            is Screen.Home -> HomeScreen()
        }
    }
}