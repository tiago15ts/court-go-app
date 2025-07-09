package pt.isel.courtandgo.frontend.reservations

import kotlinx.coroutines.test.runTest
import pt.isel.courtandgo.frontend.reservations.lastReservations.ReservationViewModel
import pt.isel.courtandgo.frontend.service.mock.MockClubService
import pt.isel.courtandgo.frontend.service.mock.MockCourtService
import pt.isel.courtandgo.frontend.service.mock.MockReservationService
import pt.isel.courtandgo.frontend.service.mock.repo.ClubRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ReservationRepoMock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class ReservationViewModelTest {

    private lateinit var viewModel: ReservationViewModel

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