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
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus
import pt.isel.courtandgo.frontend.domain.SportTypeCourt
import pt.isel.courtandgo.frontend.domain.SportsClub
import pt.isel.courtandgo.frontend.reservations.receiptReservation.ReceiptReservationScreen
import pt.isel.courtandgo.frontend.utils.dateUtils.CalendarLinkOpener
import pt.isel.courtandgo.frontend.utils.dateUtils.formatToDisplay

class ReceiptConfirmationScreenTest {
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
        clubOwnerId = 132,
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

    private val fakeReservation = Reservation(
        id= 1,
        courtId = fakeCourt.id,
        playerId = 1,
        startTime = fakeStartDateTime,
        endTime = fakeEndDateTime,
        estimatedPrice = fakeCourt.price,
        status = ReservationStatus.Pending
    )

    private val fakeCalendarOpener = object : CalendarLinkOpener {
        override fun openCalendarLink(url: String) {
            // Mock implementation
        }
    }

    @Test
    fun testReceiptConfirmationScreen_showsReservationDetails() {
        composeTestRule.setContent {
            ReceiptReservationScreen(
                reservation = fakeReservation,
                clubInfo = fakeClub,
                courtInfo = fakeCourt,
                calendarOpener = fakeCalendarOpener
            )
        }

        composeTestRule.onNodeWithText("Detalhes da Reserva").assertExists()
        composeTestRule.onNodeWithText("ID da Reserva:").assertExists()
        composeTestRule.onNodeWithText("${fakeReservation.id}").assertExists()
        composeTestRule.onNodeWithText("Clube:").assertExists()
        composeTestRule.onNodeWithText(fakeClub.name).assertExists()
        composeTestRule.onNodeWithText("Campo:").assertExists()
        composeTestRule.onNodeWithText(fakeCourt.name).assertExists()
        composeTestRule.onNodeWithText("Início:").assertExists()
        composeTestRule.onNodeWithText(formatToDisplay(fakeReservation.startTime)).assertExists()
        composeTestRule.onNodeWithText("Fim:").assertExists()
        composeTestRule.onNodeWithText(formatToDisplay(fakeReservation.endTime)).assertExists()
        composeTestRule.onNodeWithText("Preço Estimado:").assertExists()
        composeTestRule.onNodeWithText("${fakeReservation.estimatedPrice} €").assertExists()
        composeTestRule.onNodeWithText("Pode confirmar a sua reserva a partir de agora, na sua aba de reservas futuras.").assertExists()
    }


}