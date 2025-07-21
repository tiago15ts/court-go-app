package frontend.reservations

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.Rule
import org.junit.Test
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus
import pt.isel.courtandgo.frontend.repository.ClubRepositoryImpl
import pt.isel.courtandgo.frontend.repository.CourtRepositoryImpl
import pt.isel.courtandgo.frontend.repository.ReservationRepositoryImpl
import pt.isel.courtandgo.frontend.reservations.lastReservations.ReservationViewModel
import pt.isel.courtandgo.frontend.reservations.lastReservations.ReservationsScreen
import pt.isel.courtandgo.frontend.service.ReservationService
import pt.isel.courtandgo.frontend.service.mock.CourtAndGoServiceMock
import pt.isel.courtandgo.frontend.service.mock.MockClubService
import pt.isel.courtandgo.frontend.service.mock.MockCourtService
import pt.isel.courtandgo.frontend.service.mock.repo.ClubRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock
import pt.isel.courtandgo.frontend.utils.dateUtils.formatToDisplay

class ReservationsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    private val fakeStartDateTime = LocalDateTime(
        date = LocalDate(2025, 6, 20),
        time = LocalTime(11, 0)
    )

    private val fakeEndDateTime = LocalDateTime(
        date = LocalDate(2025, 6, 20),
        time = LocalTime(13, 0)
    )

    private val fakeUserId = 1

    private val fakeReservation = Reservation(
        id= 1,
        courtId = 1,
        playerId = fakeUserId,
        startTime = fakeStartDateTime,
        endTime = fakeEndDateTime,
        estimatedPrice = 15.0,
        status = ReservationStatus.Pending
    )

    private val fakeReservation2 = Reservation(
        id = 2,
        courtId = 2,
        playerId = fakeUserId,
        startTime = LocalDateTime(
            date = LocalDate(2025, 5, 21),
            time = LocalTime(16, 0)
        ),
        endTime = LocalDateTime(
            date = LocalDate(2025, 6, 21),
            time = LocalTime(17, 0)
        ),
        estimatedPrice = 12.5,
        status = ReservationStatus.Confirmed
    )


    private fun fakeReservationsViewModel(): ReservationViewModel {
        val serviceMock = CourtAndGoServiceMock()
        return ReservationViewModel(
            reservationRepo = ReservationRepositoryImpl(serviceMock),
            clubRepo = ClubRepositoryImpl(serviceMock),
            courtRepo = CourtRepositoryImpl(serviceMock)
        )
    }

    @Test
    fun testReservationsScreen() {
        composeTestRule.setContent {
            ReservationsScreen(
                viewModel = fakeReservationsViewModel(),
                userId = fakeUserId,
                onReservationClick = {},
                onBack = {}
            )
        }

        // Check if the screen displays the correct title
        composeTestRule.onNodeWithText("As suas reservas").assertExists()

        // Check if the future reservations tab is displayed
        composeTestRule.onNodeWithText("Futuras").assertExists()

        // Check if the past reservations tab is displayed
        composeTestRule.onNodeWithText("Passadas").assertExists()
    }


    @Test
    fun testReservationsScreen_displaysFutureReservations() {
        val viewModel = fakeReservationsViewModel()
        viewModel.loadReservations(fakeUserId)

        composeTestRule.setContent {
            ReservationsScreen(
                viewModel = viewModel,
                userId = fakeUserId,
                onReservationClick = {},
                onBack = {}
            )
        }

        // Check if the future reservation is displayed
        composeTestRule.onNodeWithText("Futuras").performClick()
        composeTestRule.onNodeWithText("Início: ${formatToDisplay(fakeReservation.startTime)}").assertExists()
        composeTestRule.onNodeWithText("Fim: ${formatToDisplay(fakeReservation.endTime)}").assertExists()
        composeTestRule.onNodeWithText("Preço: 15.0 €").assertExists()
    }

    @Test
    fun testReservationsScreen_displaysPastReservations() {
        val viewModel = fakeReservationsViewModel()
        viewModel.loadReservations(fakeUserId)

        composeTestRule.setContent {
            ReservationsScreen(
                viewModel = viewModel,
                userId = fakeUserId,
                onReservationClick = {},
                onBack = {}
            )
        }

        val fakeTime = LocalDateTime(
            date = LocalDate(2025, 5, 21),
            time = LocalTime(16, 0)
        )

        // Check if the past reservation is displayed
        composeTestRule.onNodeWithText("Passadas").performClick()
        composeTestRule.onNodeWithText("Início: ${formatToDisplay(fakeTime)}").assertExists()
        composeTestRule.onNodeWithText("Preço: 12.5 €").assertExists()
    }

    @Test
    fun testReservationsScreen_displaysCorrectNumberOfReservations() {
        val viewModel = fakeReservationsViewModel()
        viewModel.loadReservations(fakeUserId)

        composeTestRule.setContent {
            ReservationsScreen(
                viewModel = viewModel,
                userId = fakeUserId,
                onReservationClick = {},
                onBack = {}
            )
        }

        composeTestRule.onNodeWithText("Futuras").performClick()
        composeTestRule.onAllNodesWithText("Início: ${formatToDisplay(fakeReservation.startTime)}")
            .assertCountEquals(1)

        composeTestRule.onNodeWithText("Passadas").performClick()
        composeTestRule.onAllNodesWithText("Início: ${formatToDisplay(fakeReservation2.startTime)}")
            .assertCountEquals(1)
    }
}