package pt.isel.courtandgo.frontend.reservations

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus
import pt.isel.courtandgo.frontend.reservations.lastReservations.ReservationUiState
import pt.isel.courtandgo.frontend.reservations.lastReservations.ReservationViewModel
import kotlin.test.BeforeTest
import pt.isel.courtandgo.frontend.service.mock.MockCourtService
import pt.isel.courtandgo.frontend.service.mock.MockReservationService
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ReservationRepoMock
import pt.isel.courtandgo.frontend.service.mock.MockClubService
import pt.isel.courtandgo.frontend.service.mock.repo.ClubRepoMock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ReservationViewModelTest {

    private lateinit var viewModel: ReservationViewModel

    private val reservationsTest = mutableListOf<Reservation>(
        Reservation(1, 1, 1, LocalDateTime(2026, 5, 20, 11, 0), LocalDateTime(2026, 5, 20, 13, 0), 25.0),
        Reservation(2, 2, 1, LocalDateTime(2026, 5, 21, 16, 0), LocalDateTime(2026, 5, 21, 17, 0), 12.5),
        Reservation(3, 1, 1, LocalDateTime(2026, 5, 10, 10, 30), LocalDateTime(2026, 5, 10, 13, 0), 23.0, ReservationStatus.CONFIRMED)
    )

    @BeforeTest
    fun setup() {
        // Initialize the ReservationViewModel with mock services
        viewModel = ReservationViewModel(
            reservationService = MockReservationService(ReservationRepoMock()),
            clubService = MockClubService(ClubRepoMock()),
            courtService = MockCourtService(CourtRepoMock())
        )
    }

    @Test
    fun `getClubInfoByCourtId should return valid club`() = runTest {
        val club = viewModel.getClubInfoByCourtId(courtId = 1)
        assertNotNull(club)
        assertEquals(1, club.id)
    }

    @Test
    fun `getCourtInfoByCourtId should return valid court`() = runTest {
        val court = viewModel.getCourtInfoByCourtId(courtId = 1)
        assertNotNull(court)
        assertEquals(1, court.id)
    }

    @Test
    fun `getCourtInfoByCourtId should return null for non-existent court`() = runTest {
        assertFailsWith<IllegalArgumentException>(message = "Court não encontrado com ID: 999")
            { viewModel.getCourtInfoByCourtId(courtId = 999) }
    }

}