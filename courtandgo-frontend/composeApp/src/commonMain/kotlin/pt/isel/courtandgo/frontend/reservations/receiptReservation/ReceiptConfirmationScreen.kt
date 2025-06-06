package pt.isel.courtandgo.frontend.reservations.receiptReservation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.reservations.components.InfoRow
import pt.isel.courtandgo.frontend.utils.dateUtils.CalendarLinkOpener
import pt.isel.courtandgo.frontend.utils.dateUtils.formatToDisplay
import pt.isel.courtandgo.frontend.utils.dateUtils.generateGoogleCalendarUrl
import pt.isel.courtandgo.frontend.utils.dateUtils.timeZone
import pt.isel.courtandgo.frontend.utils.formatLocationForDisplay

@Composable
fun ReceiptReservationScreen(reservation: Reservation, clubInfo: Club, courtInfo: Court,
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

        CoilImage(
            imageModel = { "https://americanpadelsystems.com/images/portfolio/projects-1.jpg" },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp) // Altura reduzida
                .clip(RoundedCornerShape(16.dp)),
            loading = {
                CircularProgressIndicator()
            },
            failure = {
                Text("Erro ao carregar imagem")
            },
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

        Button(
            onClick = {
                val calendarUrl = generateGoogleCalendarUrl(
                    title = "Reserva CourtAndGo - ${courtInfo.name} no ${clubInfo.name}",
                    description = "Reserva feita na app CourtAndGo",
                    location = formatLocationForDisplay(clubInfo.location),
                    startTime = reservation.startTime,
                    endTime = reservation.endTime,
                    timeZone = timeZone
                )
                calendarOpener.openCalendarLink(calendarUrl)
            }
        ) {
            Text("Adicionar ao Google Calendar")
        }
    }
}

