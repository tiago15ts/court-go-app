package pt.isel.courtandgo.frontend.reservations.receiptReservation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import courtandgo_frontend.composeapp.generated.resources.Res
import courtandgo_frontend.composeapp.generated.resources.courts
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.reservations.components.InfoRow
import pt.isel.courtandgo.frontend.utils.addEventToCalendar.CalendarDropdown
import pt.isel.courtandgo.frontend.utils.addEventToCalendar.googleCalendar.AddToCalendarButton
import pt.isel.courtandgo.frontend.utils.addEventToCalendar.CalendarLinkOpener
import pt.isel.courtandgo.frontend.utils.dateUtils.formatToDisplay
import pt.isel.courtandgo.frontend.utils.dateUtils.timeZone
import pt.isel.courtandgo.frontend.utils.formatLocationForDisplay

@Composable
fun ReceiptReservationScreen(
    reservation: Reservation,
    clubInfo: Club,
    courtInfo: Court,
    calendarOpener: CalendarLinkOpener
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(Res.drawable.courts),
            contentDescription = "Courts image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                //.clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Detalhes da Reserva", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow(label = "ID da Reserva:", value = reservation.id.toString())
                InfoRow(label = "Clube:", value = clubInfo.name)
                InfoRow(label = "Campo:", value = courtInfo.name)
                InfoRow(label = "Início:", value = formatToDisplay(reservation.startTime))
                InfoRow(label = "Fim:", value = formatToDisplay(reservation.endTime))
                InfoRow(label = "Preço Estimado:", value = "${reservation.estimatedPrice} €")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Pode confirmar a sua reserva a partir de agora, na sua aba de reservas futuras.")

        Spacer(modifier = Modifier.height(16.dp))

        AddToCalendarButton(
            title = "Reserva CourtAndGo - ${courtInfo.name} no ${clubInfo.name}",
            description = "Reserva feita na app CourtAndGo",
            location = formatLocationForDisplay(clubInfo.location),
            startTime = reservation.startTime,
            endTime = reservation.endTime,
            calendarOpener = calendarOpener,
            timeZone = timeZone,
        )
        val calendarExpanded = remember { mutableStateOf(true) }

        Spacer(modifier = Modifier.height(8.dp))
        CalendarDropdown(
            reservationId = reservation.id.toString(),
            title = "Reserva CourtAndGo - ${courtInfo.name} no ${clubInfo.name}",
            location = formatLocationForDisplay(clubInfo.location),
            startTime = reservation.startTime,
            endTime = reservation.endTime,
            calendarOpener = calendarOpener,
        )

    }
}

