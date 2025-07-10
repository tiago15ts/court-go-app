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
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.Location
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus
import pt.isel.courtandgo.frontend.domain.SportTypeCourt
import pt.isel.courtandgo.frontend.reservations.lastReservations.reservationDetails.ReservationDetailsScreen
import pt.isel.courtandgo.frontend.utils.addEventToCalendar.CalendarLinkOpener
import pt.isel.courtandgo.frontend.domain.Country
import pt.isel.courtandgo.frontend.domain.District
import pt.isel.courtandgo.frontend.domain.SportsClub


class ReservationDetailsScreenTest {
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

    private val fakeReservation = Reservation(
        id= 1,
        courtId = 1,
        playerId = 1,
        startTime = fakeStartDateTime,
        endTime = fakeEndDateTime,
        estimatedPrice = 15.0,
        status = ReservationStatus.Pending
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
        id = 234,
        name = "Clube Teste",
        location = fakeLocation,
        sportsClub = SportsClub.Tennis,
        nrOfCourts = 3,
        averagePrice = 15.0
    )

    private val fakeCourt = Court(
        id = 345,
        name = "Court A",
        sportTypeCourt = SportTypeCourt.Tennis,
        surfaceType = "Terra batida",
        capacity = 6,
        price = 10.0,
        clubId = fakeClub.id,
    )

    private val fakeCalendarOpener = object : CalendarLinkOpener {
        override fun openCalendarLink(url: String) {
            // Mock implementation
        }
    }

    @Test
    fun reservationDetailsScreen_displaysCorrectDetails() {
        composeTestRule.setContent {
            ReservationDetailsScreen(
                reservation = fakeReservation,
                clubInfo = fakeClub,
                courtInfo = fakeCourt,
                calendarOpener = fakeCalendarOpener,
                onBack = {},
                onConfirmReservation = {},
                onCancelReservation = {}
            )
        }
        composeTestRule.onNodeWithText("← Voltar").assertExists()

        composeTestRule.onNodeWithText("ID da Reserva: 1").assertExists()
        composeTestRule.onNodeWithText("Clube: Clube Teste").assertExists()
        composeTestRule.onNodeWithText("Campo: Court A").assertExists()
        composeTestRule.onNodeWithText("Desporto: Ténis").assertExists()
        composeTestRule.onNodeWithText("Início: 20/06/2025 às 10:00").assertExists()
        composeTestRule.onNodeWithText("Fim: 20/06/2025 às 11:00").assertExists()
        composeTestRule.onNodeWithText("Preço Estimado: 15.0 €").assertExists()

        composeTestRule.onNodeWithText("Confirmar Reserva").assertExists()
        composeTestRule.onNodeWithText("Cancelar Reserva").assertExists()
        composeTestRule.onNodeWithText("Adicionar ao Google Calendar").assertExists()
    }

    @Test
    fun reservationDetailsScreen_displaysCorrectStatus() {
        composeTestRule.setContent {
            ReservationDetailsScreen(
                reservation = fakeReservation.copy(status = ReservationStatus.Confirmed),
                clubInfo = fakeClub,
                courtInfo = fakeCourt,
                calendarOpener = fakeCalendarOpener,
                onBack = {},
                onConfirmReservation = {},
                onCancelReservation = {}
            )
        }
        composeTestRule.onNodeWithText("✅ A sua reserva já está confirmada.").assertExists()
    }

    @Test
    fun reservationDetailsScreen_pressConfirmButton_callsOnConfirmReservation() {
        composeTestRule.setContent {
            ReservationDetailsScreen(
                reservation = fakeReservation,
                clubInfo = fakeClub,
                courtInfo = fakeCourt,
                calendarOpener = fakeCalendarOpener,
                onBack = {},
                onConfirmReservation = {},
                onCancelReservation = {}
            )
        }
        composeTestRule.onNodeWithText("Confirmar Reserva").performClick()
        composeTestRule.onNodeWithText("A confirmar...").assertExists()
    }

    @Test
    fun reservationDetailsScreen_pressCancelButton_callsOnCancelReservation() {
        composeTestRule.setContent {
            ReservationDetailsScreen(
                reservation = fakeReservation,
                clubInfo = fakeClub,
                courtInfo = fakeCourt,
                calendarOpener = fakeCalendarOpener,
                onBack = {},
                onConfirmReservation = {},
                onCancelReservation = {}
            )
        }
        composeTestRule.onNodeWithText("Cancelar Reserva").performClick()
        composeTestRule.onNodeWithText("A cancelar...").assertExists()
    }
}