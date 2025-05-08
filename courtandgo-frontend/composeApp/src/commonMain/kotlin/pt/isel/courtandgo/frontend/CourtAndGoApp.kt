package pt.isel.courtandgo.frontend

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.ui.tooling.preview.Preview
import pt.isel.courtandgo.frontend.authentication.AuthViewModel
import pt.isel.courtandgo.frontend.authentication.login.LoginScreen
import pt.isel.courtandgo.frontend.authentication.register.RegisterDetailsScreen
import pt.isel.courtandgo.frontend.authentication.register.RegisterFirstScreen
import pt.isel.courtandgo.frontend.components.LayoutScreen
import pt.isel.courtandgo.frontend.components.bottomNavBar.Tab
import pt.isel.courtandgo.frontend.home.HomeScreen
import pt.isel.courtandgo.frontend.notifications.EditNotificationsScreen
import pt.isel.courtandgo.frontend.profile.ProfileScreen
import pt.isel.courtandgo.frontend.profile.ProfileViewModel
import pt.isel.courtandgo.frontend.profile.editProfile.EditProfileScreen
import pt.isel.courtandgo.frontend.repository.AuthRepositoryImpl
import pt.isel.courtandgo.frontend.reservations.lastReservations.LastReservationsScreen
import pt.isel.courtandgo.frontend.courts.searchCourt.CourtSearchViewModel
import pt.isel.courtandgo.frontend.courts.searchCourt.SearchCourtScreen
import pt.isel.courtandgo.frontend.service.CourtAndGoService
import pt.isel.courtandgo.frontend.service.mock.MockCourtService
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock
import pt.isel.courtandgo.frontend.reservations.reservations.ReserveCourtScreen
import pt.isel.courtandgo.frontend.reservations.reservations.ReserveCourtViewModel
import pt.isel.courtandgo.frontend.service.mock.MockScheduleCourtService
import pt.isel.courtandgo.frontend.service.mock.repo.ScheduleCourtRepoMock

@Composable
@Preview
fun CourtAndGoApp(courtAndGoService: CourtAndGoService) {
    val screen = remember { mutableStateOf<Screen>(Screen.RegisterFirst) }
    val selectedTab = remember { mutableStateOf(Tab.Home) }
    val authViewModel = remember { AuthViewModel(AuthRepositoryImpl(courtAndGoService)) }
    val profileViewModel = remember { ProfileViewModel(AuthRepositoryImpl(courtAndGoService)) }
    val courtSearchViewModel = remember { CourtSearchViewModel(MockCourtService(CourtRepoMock()), MockScheduleCourtService(ScheduleCourtRepoMock())) }
    val reserveCourtViewModel = remember { ReserveCourtViewModel(MockScheduleCourtService(ScheduleCourtRepoMock())) }

    val currentUser by authViewModel.currentUser.collectAsState()

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
                    viewModel = authViewModel,
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
                    viewModel = authViewModel,
                    onRegisterSuccess = { screen.value = Screen.Home },
                    onNavigateBack = {
                        screen.value = Screen.RegisterFirst
                    }
                )

                is Screen.Login -> LoginScreen(
                    viewModel = authViewModel,
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
                        Tab.Search -> Screen.SearchCourt
                        Tab.Calendar -> Screen.LastReservations
                        Tab.Profile -> Screen.Profile
                    }
                },
                currentScreen = screen.value
            ) {
                when (screen.value) {
                    is Screen.Home -> HomeScreen(authViewModel,
                        onStartReservationClick = {screen.value= Screen.SearchCourt},
                        onLastReservationsClick = {screen.value= Screen.LastReservations}
                    )

                    is Screen.Profile -> ProfileScreen(
                        name = currentUser?.name ?: "User",
                        onEditProfile = { screen.value = Screen.EditProfile },
                        onNotifications = { screen.value = Screen.Notifications },
                        onLogout = {
                            authViewModel.logout {
                                screen.value =
                                    Screen.Login
                            }
                        }
                    )

                    Screen.EditProfile -> EditProfileScreen(
                        viewModel = profileViewModel,
                        currentUser = currentUser,
                        onBack = { screen.value = Screen.Profile },
                        onSaved = {
                            profileViewModel.user.value?.let {
                                authViewModel.setCurrentUser(it)
                            }
                            screen.value = Screen.Profile
                        }
                    )


                    Screen.Notifications -> EditNotificationsScreen()

                    is Screen.SearchCourt -> SearchCourtScreen(
                        viewModel = courtSearchViewModel,
                        onBackClick = { screen.value = Screen.Home },
                        defaultDistrict = authViewModel.currentUser.value?.location ?: "",
                        onCourtClick = { court ->
                            screen.value = Screen.Reservation(court)
                        }
                    )

                    is Screen.LastReservations -> LastReservationsScreen(
                        onReservationClick = { reservationId ->
                            //todo open reservation details
                            //screen.value = Screen.ReservationDetails(reservationId)
                        },
                        onBack = { screen.value = Screen.Home }
                    )

                    is Screen.Reservation ->
                        ReserveCourtScreen(
                            courtInfo = (screen.value as Screen.Reservation).court,
                            onBack = { screen.value = Screen.SearchCourt },
                            viewModel = reserveCourtViewModel
                        )

                    else -> {}
                }
            }
        }
    }

}
