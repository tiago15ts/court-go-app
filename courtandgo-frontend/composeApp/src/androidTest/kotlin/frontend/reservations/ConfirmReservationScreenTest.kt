package frontend.reservations

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.Rule
import org.junit.Test
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Country
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.District
import pt.isel.courtandgo.frontend.domain.Location
import pt.isel.courtandgo.frontend.domain.SportType
import pt.isel.courtandgo.frontend.reservations.confirmReservation.ConfirmReservationScreen
import pt.isel.courtandgo.frontend.reservations.confirmReservation.ConfirmReservationViewModel
import pt.isel.courtandgo.frontend.reservations.reservationTimes.CourtAvailabilityViewModel
import pt.isel.courtandgo.frontend.service.mock.MockCourtService
import pt.isel.courtandgo.frontend.service.mock.MockReservationService
import pt.isel.courtandgo.frontend.service.mock.MockScheduleCourtService
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ReservationRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ScheduleCourtRepoMock

class ConfirmReservationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun fakeConfirmReservationViewModel() : ConfirmReservationViewModel {
        return ConfirmReservationViewModel(
            reservationService = MockReservationService(ReservationRepoMock()),
            courtService = MockCourtService(CourtRepoMock()),
        )
    }

    private fun fakeAvailabilityViewModel() : CourtAvailabilityViewModel {
        return CourtAvailabilityViewModel(
            scheduleService = MockScheduleCourtService(
                ScheduleCourtRepoMock()
            ),
            reservationService = MockReservationService(
                ReservationRepoMock()
            ),
            courtService = MockCourtService(CourtRepoMock())
        )
    }

    private val countryPortugal = Country(1, "Portugal")
    private val districtLisboa = District(1,"Lisboa", 1)

    private val fakeLocation = Location(
        id = 123,
        address = "Rua Teste",
        county = "Sintra",
        district = districtLisboa,
        country = countryPortugal,
        postalCode = "2710-123",
        latitude = 38.8029,
        longitude = -9.3886
    )

    private val fakeClub = Club(
        id = 234,
        name = "Clube Teste",
        location = fakeLocation,
        sportType = SportType.TENNIS,
        nrOfCourts = 3,
        clubOwnerId = 132,
        averagePrice = 15.0
    )

    private val fakeCourt = Court(
        id = 345,
        name = "Court A",
        sportType = SportType.TENNIS,
        surfaceType = "Terra batida",
        capacity = 6,
        price = 10.0,
        clubId = fakeClub.id,
    )

    private val fakeDateTime = LocalDateTime(
        date = LocalDate(2025, 6, 20),
        time = LocalTime(10, 0)
    )

    @Test
    fun screen_renders_correctly() {
        val viewModel = fakeConfirmReservationViewModel()
        val availabilityViewModel = fakeAvailabilityViewModel()

        availabilityViewModel.loadAvailableSlots(fakeClub.id, fakeDateTime.date)

        composeTestRule.setContent {
            ConfirmReservationScreen (
                clubInfo = fakeClub,
                courtInfo = fakeCourt,
                playerId = 123,
                startDateTime = fakeDateTime,
                viewModel = viewModel,
                availabilityViewModel = availabilityViewModel,
                onReservationComplete = {},
                onBack = {}
            )
        }

        // Verifica se o nome do clube aparece
        composeTestRule.onNodeWithText("Clube: Clube Teste").assertExists()

        // Verifica se aparece a data
        composeTestRule.onNodeWithText("Data: 2025-06-20").assertExists()

        // Verifica se os botões de courts estão presentes
        composeTestRule.onNodeWithText("Campo: Court A (Terra batida)").assertExists()

        // Verifica se o botão de "Confirmar Reserva" está presente
        composeTestRule.onNodeWithText("Confirmar Reserva").assertExists()
    }
}