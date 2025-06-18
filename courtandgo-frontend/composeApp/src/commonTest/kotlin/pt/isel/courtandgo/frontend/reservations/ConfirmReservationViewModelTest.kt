package pt.isel.courtandgo.frontend.reservations


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import pt.isel.courtandgo.frontend.domain.ReservationStatus
import pt.isel.courtandgo.frontend.reservations.confirmReservation.ConfirmReservationUiState
import pt.isel.courtandgo.frontend.reservations.confirmReservation.ConfirmReservationViewModel
import pt.isel.courtandgo.frontend.service.mock.MockCourtService
import pt.isel.courtandgo.frontend.service.mock.MockReservationService
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ReservationRepoMock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.withTimeout
import pt.isel.courtandgo.frontend.service.ReservationService
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class ConfirmReservationViewModelTest {

    private lateinit var viewModel: ConfirmReservationViewModel

    @BeforeTest
    fun setup() {
        viewModel = ConfirmReservationViewModel(
            reservationService = MockReservationService(ReservationRepoMock()),
            courtService = MockCourtService(CourtRepoMock())
        )
    }


    @Test
    fun emitsIdleState_afterResetUiStateCalled() = runTest {
        val viewModel = ConfirmReservationViewModel(
            reservationService = MockReservationService(ReservationRepoMock()),
            courtService = MockCourtService(CourtRepoMock())
        )

        viewModel.placeReservation(
            playerId = 1,
            courtId = 1,
            startDateTime = LocalDateTime(2025, 6, 10, 10, 0),
            pricePerHour = 20.0
        )

        viewModel.resetUiState()

        assertEquals(ConfirmReservationUiState.Idle, viewModel.uiState.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun emitsSuccessState_whenReservationIsPlacedSuccessfully() = runTest {
        viewModel.placeReservation(
            playerId = 1,
            courtId = 1,
            startDateTime = LocalDateTime(2025, 6, 10, 10, 0),
            pricePerHour = 20.0
        )

        advanceUntilIdle()

        val result = viewModel.uiState.value
        val reservation = (result as ConfirmReservationUiState.Success).reservation
        assertEquals(1, reservation.playerId)
        assertEquals(1, reservation.courtId)
        assertEquals(LocalDateTime(2025, 6, 10, 10, 0), reservation.startTime)
        assertEquals(LocalDateTime(2025, 6, 10, 11, 0), reservation.endTime)
        assertEquals(20.0, reservation.estimatedPrice)
    }

}
