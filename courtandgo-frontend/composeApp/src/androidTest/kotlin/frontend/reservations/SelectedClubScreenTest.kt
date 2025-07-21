package frontend.reservations

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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
import pt.isel.courtandgo.frontend.domain.SportTypeCourt
import pt.isel.courtandgo.frontend.domain.SportsClub
import pt.isel.courtandgo.frontend.repository.CourtRepositoryImpl
import pt.isel.courtandgo.frontend.repository.ReservationRepositoryImpl
import pt.isel.courtandgo.frontend.repository.ScheduleRepositoryImpl
import pt.isel.courtandgo.frontend.reservations.reservationTimes.CourtAvailabilityViewModel
import pt.isel.courtandgo.frontend.reservations.reservationTimes.SelectedClubScreen
import pt.isel.courtandgo.frontend.service.mock.CourtAndGoServiceMock
import pt.isel.courtandgo.frontend.service.mock.MockCourtService
import pt.isel.courtandgo.frontend.service.mock.MockReservationService
import pt.isel.courtandgo.frontend.service.mock.MockScheduleCourtService
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ReservationRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ScheduleCourtRepoMock
import pt.isel.courtandgo.frontend.utils.toPortugueseName

class SelectedClubScreenTest {

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
        id = 1,
        name = "Clube Teste",
        location = fakeLocation,
        sportsClub = SportsClub.Tennis,
        nrOfCourts = 3,
        averagePrice = 15.0
    )

    private val fakeCourt = Court(
        id = 1,
        name = "Court A",
        sportTypeCourt = SportTypeCourt.Tennis,
        surfaceType = "Terra batida",
        capacity = 6,
        price = 10.0,
        clubId = fakeClub.id,
    )

    private fun fakeAvailabilityViewModel() : CourtAvailabilityViewModel {
        val serviceMock = CourtAndGoServiceMock()
        return CourtAvailabilityViewModel(
            scheduleRepo = ScheduleRepositoryImpl(serviceMock),
            reservationRepo = ReservationRepositoryImpl(serviceMock),
            courtRepo = CourtRepositoryImpl(serviceMock)
        )
    }

    @Test
    fun testSelectedClubScreen() {
        composeTestRule.setContent {
            SelectedClubScreen(
                clubInfo = fakeClub,
                courtInfo = fakeCourt,
                onBack = {},
                courtAvailabilityViewModel = fakeAvailabilityViewModel(),
                onContinueToConfirmation = {}
            )
        }

        composeTestRule.onNodeWithText(fakeClub.name).assertExists()
        composeTestRule.onNodeWithText(fakeClub.location.district.name).assertExists()
        composeTestRule.onNodeWithText("Detalhes").assertExists()
        composeTestRule.onNodeWithText("Reservar").assertExists()
        composeTestRule.onNodeWithText("Mostrar apenas as horas disponíveis").assertExists()
        composeTestRule.onNodeWithText("10:00").assertExists()
    }

    @Test
    fun testSelectDetailsOfAClub() {
        composeTestRule.setContent {
            SelectedClubScreen(
                clubInfo = fakeClub,
                courtInfo = fakeCourt,
                onBack = {},
                courtAvailabilityViewModel = fakeAvailabilityViewModel(),
                onContinueToConfirmation = {}
            )
        }
        composeTestRule.onNodeWithText("Clube Teste").assertExists()
        composeTestRule.onNodeWithText("Detalhes").performClick()

        composeTestRule.onNodeWithText(fakeCourt.sportTypeCourt.toPortugueseName()).assertExists()
        composeTestRule.onNodeWithText("Tipo de piso: ${fakeCourt.surfaceType}").assertExists()
        composeTestRule.onNodeWithText("Capacidade por campo: ${fakeCourt.capacity} pessoas").assertExists()
        composeTestRule.onNodeWithText("O clube tem ${fakeClub.nrOfCourts} campos disponíveis.").assertExists()
        composeTestRule.onNodeWithText("Localização: ${fakeClub.location.address}," +
                " ${fakeClub.location.county}," +
                " ${fakeClub.location.district}," +
                " ${fakeClub.location.postalCode}").assertExists()
    }

    @Test
    fun testSelectReserveTab() {
        composeTestRule.setContent {
            SelectedClubScreen(
                clubInfo = fakeClub,
                courtInfo = fakeCourt,
                onBack = {},
                courtAvailabilityViewModel = fakeAvailabilityViewModel(),
                onContinueToConfirmation = {}
            )
        }

        composeTestRule.onNodeWithText("Reservar").performClick()
        composeTestRule.onNodeWithText("Mostrar apenas as horas disponíveis").assertExists()
        composeTestRule.onNodeWithText("10:00").assertExists()
        composeTestRule.onNodeWithText("10:00").performClick()
        composeTestRule.onNodeWithText("Continuar").assertExists()
        composeTestRule.onNodeWithText("Continuar").performClick()
    }

}