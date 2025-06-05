package pt.isel.courtandgo.frontend.reservations.confirmReservation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.raedghazal.kotlinx_datetime_ext.plus
import kotlinx.datetime.LocalDateTime
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.reservations.reservationTimes.CourtAvailabilityViewModel
import kotlin.time.Duration.Companion.minutes


@Composable
fun ConfirmReservationScreen(
    clubInfo: Club,
    courtInfo: Court,
    playerId: Int,
    startDateTime: LocalDateTime,
    viewModel: ConfirmReservationViewModel,
    availabilityViewModel: CourtAvailabilityViewModel,
    onReservationComplete: (Reservation) -> Unit,
    onBack: () -> Unit
) {
    val duration = viewModel.durationInMinutes.value
    val isSubmitting = viewModel.isSubmitting.value
    val reservationConfirmed = viewModel.reservationConfirmed.value

    val selectedCourtId = viewModel.selectedCourtId.value
    val endDateTime = startDateTime.plus(duration.minutes)
    val estimatedPrice = (duration / 60.0 * courtInfo.price)

    val allCourts = viewModel.courts.value
    val availableCourtsAtTime = availabilityViewModel.availableSlots.value
        .filterValues { times -> startDateTime.time in times }
        .keys

    val availableCourts = allCourts.filter { it.id in availableCourtsAtTime }


    LaunchedEffect(reservationConfirmed, startDateTime) {
        viewModel.loadCourts(clubInfo.id, availableCourtsAtTime)
        if (reservationConfirmed != null) {
            onReservationComplete(reservationConfirmed)
            viewModel.clearReservationConfirmed()
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Column(modifier = Modifier.padding(16.dp)) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar"
            )
        }

        Text("Clube: ${clubInfo.name}", style = MaterialTheme.typography.titleLarge)
        Text("Data: ${startDateTime.date}")
        Text("Hora de início: ${startDateTime.time}")

        Spacer(modifier = Modifier.height(8.dp))

        Text("Selecione o campo pretendido:")

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            availableCourts.forEach { court ->
                val isSelected = viewModel.selectedCourtId.value == court.id
                Button(
                    onClick = { viewModel.setSelectedCourt(court.id) },
                    colors = if (isSelected) {
                        ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White)
                    } else {
                        ButtonDefaults.buttonColors(containerColor = Color.LightGray, contentColor = Color.Black)
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(court.name)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Seleciona a duração:")
        Row {
            listOf(60, 90, 120).forEach { minutes ->
                val isSelected = duration == minutes
                Button(
                    onClick = { viewModel.setDuration(minutes) },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = if (isSelected) {
                        ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White)
                    } else {
                        ButtonDefaults.buttonColors(containerColor = Color.LightGray, contentColor = Color.Black)
                    },
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
                    courtId = selectedCourtId ?: courtInfo.id,
                    startDateTime = startDateTime,
                    pricePerHour = courtInfo.price
                )
            },
            enabled = !isSubmitting,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isSubmitting) "A processar..." else "Confirmar Reserva")
        }
    }
}
