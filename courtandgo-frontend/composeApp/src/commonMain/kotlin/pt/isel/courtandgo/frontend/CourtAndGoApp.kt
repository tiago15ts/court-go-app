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
import pt.isel.courtandgo.frontend.components.LayoutScreen
import pt.isel.courtandgo.frontend.components.bottomNavBar.Tab
import pt.isel.courtandgo.frontend.domain.User
import pt.isel.courtandgo.frontend.home.HomeScreen
import pt.isel.courtandgo.frontend.notifications.EditNotificationsScreen
import pt.isel.courtandgo.frontend.profile.ProfileScreen
import pt.isel.courtandgo.frontend.profile.editProfile.EditProfileScreen
import pt.isel.courtandgo.frontend.repository.AuthRepositoryImpl
import pt.isel.courtandgo.frontend.service.CourtAndGoService

@Composable
fun CourtAndGoApp(courtAndGoService: CourtAndGoService) {
    val screen = remember { mutableStateOf<Screen>(Screen.RegisterFirst) }
    val coroutineScope = rememberCoroutineScope()
    val loginViewModel = remember { LoginViewModel(AuthRepositoryImpl(courtAndGoService)) }
    //val profileViewModel = //todo remember { ProfileViewModel(courtAndGoService) }
    val selectedTab = remember { mutableStateOf(Tab.Home) }

    val currentUser = remember { mutableStateOf<User?>(null) } //todo get user from service


    val isAuthenticated = when (screen.value) {
        is Screen.Login,
        is Screen.RegisterFirst,
        is Screen.RegisterDetails -> false
        else -> true
    }

    MaterialTheme {
        if (!isAuthenticated) {
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
                    onRegister = { email, name, countryCode, contact, password ->
                        coroutineScope.launch {
                            courtAndGoService.userService.register(email, name,countryCode, contact, password)
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

                else -> {}
            }
        } else {
            LayoutScreen(
                selectedTab = selectedTab.value,
                onTabSelected = { tab ->
                    selectedTab.value = tab
                    screen.value = when (tab) {
                        Tab.Home -> Screen.Home
                        Tab.Search -> Screen.Search
                        Tab.Calendar -> Screen.Calendar
                        Tab.Profile -> Screen.Profile
                    }
                },
                currentScreen = screen.value
            ) {
                when (screen.value) {
                    is Screen.Home -> HomeScreen(loginViewModel) //todo fix home deve receber vm de login e de registo

                    is Screen.Profile -> ProfileScreen(
                        name = loginViewModel.userName ?: "User",
                        onEditProfile = { screen.value = Screen.EditProfile },
                        onNotifications = { screen.value = Screen.Notifications },
                        onLogout = { screen.value = Screen.Login }
                    )

                    Screen.EditProfile -> EditProfileScreen(
                        user = User(1, //todo get user from service
                            loginViewModel.userName ?: "user",
                            "email",
                            "countryCode",
                            "phone",
                            null,
                            null,
                            null),
                        onBack = { screen.value = Screen.Profile },
                        onSave = { updatedUser ->
                            currentUser.value = updatedUser
                            screen.value = Screen.Profile
                        },
                        onChangePhoto = {
                            //todo change photo
                        }
                    )
                    Screen.Notifications -> EditNotificationsScreen()

                    is Screen.Search -> TODO()
                    is Screen.Calendar -> TODO()
                    else -> {}
                }
            }
        }
    }
}
