package pt.isel.courtandgo.frontend.reservations.confirmReservation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raedghazal.kotlinx_datetime_ext.plus
import kotlinx.datetime.LocalDateTime
import pt.isel.courtandgo.frontend.domain.Reservation
import kotlin.time.Duration.Companion.minutes


@Composable
fun ConfirmReservationScreen(
    courtName: String,
    playerId: Int,
    courtId: Int,
    startDateTime: LocalDateTime,
    pricePerHour: Double,
    viewModel: ConfirmReservationViewModel,
    onReservationComplete: (Reservation) -> Unit
) {
    val duration = viewModel.durationInMinutes.value
    val isSubmitting = viewModel.isSubmitting.value
    val reservationConfirmed = viewModel.reservationConfirmed.value

    //todo add possibility to choose court like court 1, 2, etc.

    LaunchedEffect(reservationConfirmed) {
        if (reservationConfirmed != null) {
            onReservationComplete(reservationConfirmed)
            viewModel.clearReservationConfirmed()
        }
    }

    val endDateTime = startDateTime.plus(duration.minutes)
    val estimatedPrice = (duration / 60.0 * pricePerHour)

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Campo: $courtName", style = MaterialTheme.typography.titleLarge)
        Text("Data: ${startDateTime.date}")
        Text("Hora de início: ${startDateTime.time}")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Seleciona a duração:")
        Row {
            listOf(60, 90, 120).forEach { minutes ->
                Button(
                    onClick = { viewModel.setDuration(minutes) },
                    modifier = Modifier.padding(end = 8.dp),
                    enabled = duration != minutes
                ) {
                    Text("${minutes / 60}h${if (minutes % 60 > 0) " ${minutes % 60}min" else ""}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Hora de fim: ${endDateTime.time}")
        Text("Preço estimado: $estimatedPrice €")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.confirmReservation(
                    playerId = playerId,
                    courtId = courtId,
                    startDateTime = startDateTime,
                    pricePerHour = pricePerHour
                )
            },
            enabled = !isSubmitting,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isSubmitting) "A processar..." else "Confirmar Reserva")
        }
    }
}
