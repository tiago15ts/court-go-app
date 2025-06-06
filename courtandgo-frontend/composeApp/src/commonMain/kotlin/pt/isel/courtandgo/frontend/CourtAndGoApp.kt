package pt.isel.courtandgo.frontend

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import pt.isel.courtandgo.frontend.authentication.AuthViewModel
import pt.isel.courtandgo.frontend.authentication.login.LoginScreen
import pt.isel.courtandgo.frontend.authentication.register.RegisterDetailsScreen
import pt.isel.courtandgo.frontend.authentication.register.RegisterFirstScreen
import pt.isel.courtandgo.frontend.clubs.searchClub.SearchClubScreen
import pt.isel.courtandgo.frontend.clubs.searchClub.SearchClubViewModel
import pt.isel.courtandgo.frontend.components.LayoutScreen
import pt.isel.courtandgo.frontend.components.bottomNavBar.Tab
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Location
import pt.isel.courtandgo.frontend.domain.SportType
import pt.isel.courtandgo.frontend.home.HomeScreen
import pt.isel.courtandgo.frontend.notifications.EditNotificationsScreen
import pt.isel.courtandgo.frontend.notifications.NotificationSettingsViewModel
import pt.isel.courtandgo.frontend.profile.ProfileScreen
import pt.isel.courtandgo.frontend.profile.ProfileViewModel
import pt.isel.courtandgo.frontend.profile.editProfile.EditProfileScreen
import pt.isel.courtandgo.frontend.repository.AuthRepositoryImpl
import pt.isel.courtandgo.frontend.reservations.confirmReservation.ConfirmReservationScreen
import pt.isel.courtandgo.frontend.reservations.confirmReservation.ConfirmReservationViewModel
import pt.isel.courtandgo.frontend.reservations.lastReservations.ReservationViewModel
import pt.isel.courtandgo.frontend.reservations.lastReservations.ReservationsScreen
import pt.isel.courtandgo.frontend.reservations.lastReservations.reservationDetails.ReservationDetailsScreen
import pt.isel.courtandgo.frontend.reservations.receiptReservation.ReceiptReservationScreen
import pt.isel.courtandgo.frontend.reservations.reservationTimes.CourtAvailabilityViewModel
import pt.isel.courtandgo.frontend.reservations.reservationTimes.SelectedClubScreen
import pt.isel.courtandgo.frontend.service.CourtAndGoService
import pt.isel.courtandgo.frontend.service.mock.MockClubService
import pt.isel.courtandgo.frontend.service.mock.MockCourtService
import pt.isel.courtandgo.frontend.service.mock.MockReservationService
import pt.isel.courtandgo.frontend.service.mock.MockScheduleCourtService
import pt.isel.courtandgo.frontend.service.mock.repo.ClubRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ReservationRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ScheduleCourtRepoMock
import pt.isel.courtandgo.frontend.utils.dateUtils.CalendarLinkOpener


@Composable
@Preview
fun CourtAndGoApp(courtAndGoService: CourtAndGoService, calendarLinkOpener: CalendarLinkOpener) {
    val screen = remember { mutableStateOf<Screen>(Screen.RegisterFirst) }
    val selectedTab = remember { mutableStateOf(Tab.Home) }
    val authViewModel = remember { AuthViewModel(AuthRepositoryImpl(courtAndGoService)) }
    val profileViewModel = remember { ProfileViewModel(AuthRepositoryImpl(courtAndGoService)) }

    val scheduleServiceShared = remember { MockScheduleCourtService(ScheduleCourtRepoMock()) }
    val reservationServiceShared = remember { MockReservationService(ReservationRepoMock()) }

    val searchClubViewModel = remember { SearchClubViewModel(
        MockClubService(ClubRepoMock()),
        scheduleServiceShared,
        MockCourtService(CourtRepoMock())
    ) }
    val reservationVm = remember {
        ReservationViewModel(reservationServiceShared, MockClubService(ClubRepoMock()),
            MockCourtService(CourtRepoMock())
        )
    }
    val notificationVm = remember {
        NotificationSettingsViewModel()
    }
    val confirmationVm = remember {
        ConfirmReservationViewModel(reservationServiceShared, MockCourtService(CourtRepoMock()))
    }
    val courtAvailabilityViewModel = remember { CourtAvailabilityViewModel(
        scheduleServiceShared,
        reservationServiceShared,
        MockCourtService(CourtRepoMock())
    ) }

    val currentUser by authViewModel.currentUser.collectAsState()

    val isAuthenticated = when (screen.value) {
        is Screen.Login,
        is Screen.RegisterFirst,
        is Screen.RegisterDetails -> false
        else -> true
    }
    val scope = rememberCoroutineScope()

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
                        Tab.Search -> Screen.SearchClub
                        Tab.Calendar -> Screen.LastReservations
                        Tab.Profile -> Screen.Profile
                    }
                },
                currentScreen = screen.value
            ) {
                when (screen.value) {
                    is Screen.Home -> HomeScreen(authViewModel,
                        onStartReservationClick = {screen.value= Screen.SearchClub},
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

                    Screen.Notifications -> EditNotificationsScreen(notificationVm, reservationVm.futureReservations.value)

                    is Screen.SearchClub -> SearchClubScreen(
                        viewModel = searchClubViewModel,
                        onBackClick = { screen.value = Screen.Home },
                        onClubClick = { club ->
                            scope.launch {
                                val courts = searchClubViewModel.getCourtsForClub(club.id)
                                if (courts.isNotEmpty()) {
                                    val court = courts.first()
                                    screen.value = Screen.SelectedClub(club, court)
                                } else {
                                    // mostra aviso ou lida com ausência de courts
                                }
                            }
                        }
                    )

                    is Screen.LastReservations -> ReservationsScreen(
                        viewModel = reservationVm,
                        userId = currentUser?.id ?: 0,
                        onReservationClick = { reservation ->
                            scope.launch {
                                screen.value =
                                    Screen.ReservationDetails(reservation,
                                        clubInfo = reservationVm.getClubInfoByCourtId(reservation.courtId) ?: Club(
                                            id = -1,
                                            name = "Unknown Club",
                                            location = Location(
                                                id = -1,
                                                address = "Unknown Address",
                                                county = "Unknown County",
                                                district = "Unknown District",
                                                country = "Unknown Country",
                                                postalCode = "0000-000",
                                                latitude = 0.0,
                                                longitude = 0.0
                                            ),
                                            sportType = SportType.BOTH,
                                            nrOfCourts = 0,
                                            clubOwnerId = 0,
                                            averagePrice = 0.0
                                        ),
                                        courtInfo = reservationVm.getCourtInfoByCourtId(reservation.courtId)
                                    )
                            }
                        },
                        onBack = { screen.value = Screen.Home }
                    )

                    is Screen.SelectedClub ->
                        SelectedClubScreen(
                            clubInfo = (screen.value as Screen.SelectedClub).club,
                            courtInfo = (screen.value as Screen.SelectedClub).court,
                            onBack = { screen.value = Screen.SearchClub },
                            courtAvailabilityViewModel = courtAvailabilityViewModel,
                            onContinueToConfirmation = { dateTime ->
                                screen.value = Screen.ConfirmReservation(
                                    clubInfo = (screen.value as Screen.SelectedClub).club,
                                    courtInfo = (screen.value as Screen.SelectedClub).court,
                                    playerId = currentUser?.id ?: 0, //todo fix this nullablecase
                                    startDateTime = dateTime
                                )
                            }
                        )

                    is Screen.ReservationDetails -> ReservationDetailsScreen(
                        reservation = (screen.value as Screen.ReservationDetails).reservation,
                        clubInfo = (screen.value as Screen.ReservationDetails).clubInfo,
                        courtInfo = (screen.value as Screen.ReservationDetails).courtInfo,
                        calendarOpener = calendarLinkOpener,
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
                        clubInfo = (screen.value as Screen.ConfirmReservation).clubInfo,
                        courtInfo = (screen.value as Screen.ConfirmReservation).courtInfo,
                        playerId = (screen.value as Screen.ConfirmReservation).playerId,
                        startDateTime = (screen.value as Screen.ConfirmReservation).startDateTime,
                        viewModel = confirmationVm,
                        availabilityViewModel = courtAvailabilityViewModel,
                        onReservationComplete = { reservation ->
                            screen.value = Screen.ReceiptReservation(
                                reservation,
                                (screen.value as Screen.ConfirmReservation).clubInfo,
                                (screen.value as Screen.ConfirmReservation).courtInfo
                            )
                        },
                        onBack = {
                            screen.value = Screen.SelectedClub(
                                (screen.value as Screen.ConfirmReservation).clubInfo,
                                (screen.value as Screen.ConfirmReservation).courtInfo
                            )
                        }
                    )

                    is Screen.ReceiptReservation -> ReceiptReservationScreen(
                        reservation = (screen.value as Screen.ReceiptReservation).reservation,
                        clubInfo = (screen.value as Screen.ReceiptReservation).clubInfo,
                        courtInfo = (screen.value as Screen.ReceiptReservation).courtInfo,
                        calendarOpener = calendarLinkOpener,
                    )
                    else -> {}
                }
            }
        }
    }

}
