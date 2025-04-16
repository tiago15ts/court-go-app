package pt.isel.courtandgo.frontend

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import pt.isel.courtandgo.frontend.authentication.login.LoginScreen
import pt.isel.courtandgo.frontend.authentication.login.LoginViewModel
import pt.isel.courtandgo.frontend.authentication.register.RegisterDetailsScreen
import pt.isel.courtandgo.frontend.authentication.register.RegisterFirstScreen
import pt.isel.courtandgo.frontend.components.bottomNavBar.Tab
import pt.isel.courtandgo.frontend.home.HomeScreen
import pt.isel.courtandgo.frontend.repository.AuthRepository
import pt.isel.courtandgo.frontend.repository.AuthRepositoryImpl
import pt.isel.courtandgo.frontend.repository.AuthViewModel
import pt.isel.courtandgo.frontend.service.CourtAndGoService

@Composable
fun CourtAndGoApp(courtAndGoService: CourtAndGoService) {
    val screen = remember { mutableStateOf<Screen>(Screen.RegisterFirst) }
    val coroutineScope = rememberCoroutineScope()
    val loginViewModel = remember { LoginViewModel(AuthRepositoryImpl(courtAndGoService)) }
    val selectedTab = remember { mutableStateOf(Tab.Home) }


    MaterialTheme {
        when (val current = screen.value) {
            is Screen.RegisterFirst -> RegisterFirstScreen(
                onContinueWithEmail = { email ->
                    screen.value = Screen.RegisterDetails(email)
                },
                onGoogleRegister = { tokenId ->
                    // autentica com courtAndGoService.userService.loginComGoogle(...)
                    screen.value = Screen.Home
                },
                onAlreadyHaveAccount = {
                    screen.value = Screen.Login
                }
            )

            is Screen.RegisterDetails -> RegisterDetailsScreen(
                email = current.email,
                onRegister = { email, name, contact, password ->
                    coroutineScope.launch {
                        courtAndGoService.userService.register(email, name, contact, password)
                        screen.value = Screen.Home
                    }
                },
                onNavigateBack = {
                    screen.value = Screen.RegisterFirst
                }
            )


            is Screen.Login -> LoginScreen(
                viewModel = loginViewModel,
                onNavigateBack = { screen.value = Screen.RegisterFirst },
                onLoginSuccess = { screen.value = Screen.Home }
            )

            is Screen.Home -> HomeScreen(loginViewModel) //todo fix here
        }
    }
}
