package pt.isel.courtandgo.frontend.reservations.confirmReservation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.raedghazal.kotlinx_datetime_ext.plus
import kotlinx.datetime.LocalDateTime
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.reservations.reservationTimes.CourtAvailabilityUiState
import pt.isel.courtandgo.frontend.reservations.reservationTimes.CourtAvailabilityViewModel
import kotlin.time.Duration.Companion.minutes
import androidx.compose.material3.CircularProgressIndicator


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
    val uiState by viewModel.uiState

    val selectedCourtId = viewModel.selectedCourtId.value
    val endDateTime = startDateTime.plus(duration.minutes)
    val estimatedPrice = (duration / 60.0 * courtInfo.price)

    val allCourts = viewModel.courts.value

    val availabilityUiState by availabilityViewModel.uiState

    val availableSlots =
        (availabilityUiState as CourtAvailabilityUiState.Success).availableSlots
    val availableCourtsAtTime = availableSlots
        .filterValues { times -> startDateTime.time in times }
        .keys

    val availableCourts = allCourts.filter { it.id in availableCourtsAtTime }

    LaunchedEffect(uiState, startDateTime) {
        viewModel.loadCourts(clubInfo.id, availableCourtsAtTime)
        when (val state = uiState) {
            is ConfirmReservationUiState.Success -> {
                onReservationComplete(state.reservation)
                viewModel.resetUiState()
            }
            else -> Unit
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
        Text("Campo: ${courtInfo.name} (${courtInfo.surfaceType})")
        Text("Hora de início: ${startDateTime.time}")

        Spacer(modifier = Modifier.height(8.dp))

        when (availabilityUiState) {
            is CourtAvailabilityUiState.Loading -> {
                Text("A carregar disponibilidade...")
            }
            is CourtAvailabilityUiState.Error -> {
                Text(
                    text = (availabilityUiState as CourtAvailabilityUiState.Error).message,
                    color = Color.Red
                )
            }

            is CourtAvailabilityUiState.Success -> {

                Text("Selecione o campo pretendido:")

                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    availableCourts.forEach { court ->
                        val isSelected = viewModel.selectedCourtId.value == court.id
                        Button(
                            onClick = { viewModel.setSelectedCourt(court.id) },
                            colors = if (isSelected) {
                                ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                )
                            } else {
                                ButtonDefaults.buttonColors(
                                    containerColor = Color.LightGray,
                                    contentColor = Color.Black
                                )
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
                                ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                )
                            } else {
                                ButtonDefaults.buttonColors(
                                    containerColor = Color.LightGray,
                                    contentColor = Color.Black
                                )
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

                val isLoading = uiState is ConfirmReservationUiState.Loading

                Button(
                    onClick = {
                        viewModel.placeReservation(
                            playerId = playerId,
                            courtId = selectedCourtId ?: courtInfo.id,
                            startDateTime = startDateTime,
                            pricePerHour = courtInfo.price
                        )
                    },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .height(16.dp)
                                .width(16.dp),
                            color = Color.Gray,
                            strokeWidth = 2.dp
                        )
                        Text("A processar...")
                    } else {
                        Text("Confirmar Reserva")
                    }
                }
                if (uiState is ConfirmReservationUiState.Error) {
                    Text(
                        text = (uiState as ConfirmReservationUiState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
