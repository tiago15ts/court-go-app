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
import pt.isel.courtandgo.frontend.reservations.lastReservations.ReservationViewModel
import pt.isel.courtandgo.frontend.reservations.lastReservations.ReservationsScreen
import pt.isel.courtandgo.frontend.service.ReservationService
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
        time = LocalTime(10, 0)
    )

    private val fakeEndDateTime = LocalDateTime(
        date = LocalDate(2025, 6, 20),
        time = LocalTime(11, 0)
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
            date = LocalDate(2024, 6, 21),
            time = LocalTime(10, 0)
        ),
        endTime = LocalDateTime(
            date = LocalDate(2024, 6, 21),
            time = LocalTime(11, 0)
        ),
        estimatedPrice = 20.0,
        status = ReservationStatus.Confirmed
    )

    private val fakeList = listOf(fakeReservation, fakeReservation2)


    private val fakeReservationService = object : ReservationService {
        override suspend fun getReservations(): List<Reservation> = fakeList

        override suspend fun getReservationById(id: Int): Reservation = fakeReservation

        override suspend fun getReservationsForPlayer(playerId: Int): List<Reservation> = fakeList

        override suspend fun createReservation(reservation: Reservation): Reservation =
            fakeReservation.copy(id = 2) // Simulate a new reservation with a different ID

        override suspend fun updateReservation(reservation: Reservation): Reservation? =
            if (reservation.id == fakeReservation.id) fakeReservation else null

        override suspend fun cancelReservation(id: Int): Boolean =
            id == fakeReservation.id

        override suspend fun setConfirmedReservation(id: Int): Boolean =
            id == fakeReservation.id

        override suspend fun getReservationsByCourtIdsAndDate(
            courtIds: List<Int>,
            date: LocalDate
        ): List<Reservation> = fakeList

    }

    private fun fakeReservationsViewModel(): ReservationViewModel {
        return ReservationViewModel(
            reservationRepo = fakeReservationService,
            clubRepo = MockClubService(ClubRepoMock()),
            courtRepo = MockCourtService(CourtRepoMock())
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
            date = LocalDate(2024, 6, 21),
            time = LocalTime(10, 0)
        )

        // Check if the past reservation is displayed
        composeTestRule.onNodeWithText("Passadas").performClick()
        composeTestRule.onNodeWithText("Início: ${formatToDisplay(fakeTime)}").assertExists()
        composeTestRule.onNodeWithText("Preço: 20.0 €").assertExists()
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