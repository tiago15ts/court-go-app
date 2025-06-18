package pt.isel.courtandgo.frontend.reservations

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.datetime.LocalDate
import pt.isel.courtandgo.frontend.reservations.reservationTimes.CourtAvailabilityUiState
import pt.isel.courtandgo.frontend.reservations.reservationTimes.CourtAvailabilityViewModel
import pt.isel.courtandgo.frontend.service.mock.MockClubService
import pt.isel.courtandgo.frontend.service.mock.MockCourtService
import pt.isel.courtandgo.frontend.service.mock.MockReservationService
import pt.isel.courtandgo.frontend.service.mock.MockScheduleCourtService
import pt.isel.courtandgo.frontend.service.mock.repo.ClubRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalTime
import pt.isel.courtandgo.frontend.service.mock.repo.ReservationRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ScheduleCourtRepoMock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals

class CourtAvailabilityViewModelTest {

    private lateinit var viewModel: CourtAvailabilityViewModel

    @BeforeTest
    fun setup() {
        // Initialize the CourtAvailabilityViewModel with mock services
        viewModel = CourtAvailabilityViewModel(
            scheduleService = MockScheduleCourtService(ScheduleCourtRepoMock()),
            reservationService = MockReservationService(ReservationRepoMock()),
            courtService = MockCourtService(CourtRepoMock())
        )
    }


}