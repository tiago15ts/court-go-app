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
import pt.isel.courtandgo.frontend.courts.searchCourt.CourtSearchViewModel
import pt.isel.courtandgo.frontend.courts.searchCourt.SearchCourtScreen
import pt.isel.courtandgo.frontend.home.HomeScreen
import pt.isel.courtandgo.frontend.notifications.EditNotificationsScreen
import pt.isel.courtandgo.frontend.profile.ProfileScreen
import pt.isel.courtandgo.frontend.profile.ProfileViewModel
import pt.isel.courtandgo.frontend.profile.editProfile.EditProfileScreen
import pt.isel.courtandgo.frontend.repository.AuthRepositoryImpl
import pt.isel.courtandgo.frontend.reservations.confirmReservation.ConfirmReservationScreen
import pt.isel.courtandgo.frontend.reservations.confirmReservation.ConfirmReservationViewModel
import pt.isel.courtandgo.frontend.reservations.confirmReservation.ReceiptReservationScreen
import pt.isel.courtandgo.frontend.reservations.lastReservations.ReservationViewModel
import pt.isel.courtandgo.frontend.reservations.lastReservations.ReservationsScreen
import pt.isel.courtandgo.frontend.reservations.lastReservations.reservationDetails.ReservationDetailsScreen
import pt.isel.courtandgo.frontend.reservations.reservationTimes.ReserveCourtViewModel
import pt.isel.courtandgo.frontend.reservations.reservationTimes.SelectedCourtScreen
import pt.isel.courtandgo.frontend.service.CourtAndGoService
import pt.isel.courtandgo.frontend.service.mock.MockCourtService
import pt.isel.courtandgo.frontend.service.mock.MockReservationService
import pt.isel.courtandgo.frontend.service.mock.MockScheduleCourtService
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ReservationRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ScheduleCourtRepoMock

@Composable
@Preview
fun CourtAndGoApp(courtAndGoService: CourtAndGoService) {
    val screen = remember { mutableStateOf<Screen>(Screen.RegisterFirst) }
    val selectedTab = remember { mutableStateOf(Tab.Home) }
    val authViewModel = remember { AuthViewModel(AuthRepositoryImpl(courtAndGoService)) }
    val profileViewModel = remember { ProfileViewModel(AuthRepositoryImpl(courtAndGoService)) }

    val sharedScheduleService = remember { MockScheduleCourtService(ScheduleCourtRepoMock()) }
    val courtSearchViewModel = remember { CourtSearchViewModel(MockCourtService(CourtRepoMock()), sharedScheduleService) }
    val reserveCourtViewModel = remember { ReserveCourtViewModel(sharedScheduleService) }

    val sharedReservationService = remember { MockReservationService(ReservationRepoMock()) }
    val reservationVm = remember {
        ReservationViewModel(sharedReservationService, MockCourtService(CourtRepoMock()))
    }
    val confirmationVm = remember {
        ConfirmReservationViewModel(sharedReservationService)
    }

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
                            screen.value = Screen.ReserveCourt(court)
                        }
                    )

                    is Screen.LastReservations -> ReservationsScreen(
                        viewModel = reservationVm,
                        userId = currentUser?.id ?: 0,
                        onReservationClick = { reservation ->
                            screen.value = Screen.ReservationDetails(reservation)
                        },
                        onBack = { screen.value = Screen.Home }
                    )

                    is Screen.ReserveCourt ->
                        SelectedCourtScreen(
                            courtInfo = (screen.value as Screen.ReserveCourt).court,
                            onBack = { screen.value = Screen.SearchCourt },
                            reserveCourtViewModel = reserveCourtViewModel,
                            onContinueToConfirmation = { court, dateTime ->
                                screen.value = Screen.ConfirmReservation(
                                    courtId = court.id,
                                    courtName = court.name,
                                    playerId = currentUser?.id ?: 0, //todo fix this nullablecase
                                    startDateTime = dateTime,
                                    pricePerHour = court.price
                                )
                            }
                        )

                    is Screen.ReservationDetails -> ReservationDetailsScreen(
                        reservation = (screen.value as Screen.ReservationDetails).reservation,
                        onBack = { screen.value = Screen.LastReservations },
                        onConfirmReservation = { reservation ->
                            confirmationVm.confirmReservation(reservation)
                            screen.value = Screen.LastReservations
                        },
                        onCancelReservation = { reservation ->
                            reservationVm.cancelReservation(reservation)
                            screen.value = Screen.LastReservations
                        }
                    )

                    is Screen.ConfirmReservation -> ConfirmReservationScreen(
                        courtName = (screen.value as Screen.ConfirmReservation).courtName,
                        playerId = (screen.value as Screen.ConfirmReservation).playerId,
                        courtId = (screen.value as Screen.ConfirmReservation).courtId,
                        startDateTime = (screen.value as Screen.ConfirmReservation).startDateTime,
                        pricePerHour = (screen.value as Screen.ConfirmReservation).pricePerHour,
                        viewModel = confirmationVm,
                        onReservationComplete = { reservation ->
                            screen.value = Screen.ReceiptReservation(reservation)
                        }
                    )

                    is Screen.ReceiptReservation -> ReceiptReservationScreen(
                        reservation = (screen.value as Screen.ReceiptReservation).reservation,
                    )
                    else -> {}
                }
            }
        }
    }

}
