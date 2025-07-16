package pt.isel.courtandgo.frontend.reservations.reservationTimes.sections

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.utils.dateUtils.DatePickerRow
import pt.isel.courtandgo.frontend.utils.dateUtils.currentDate
import pt.isel.courtandgo.frontend.utils.dateUtils.currentTime
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.reservations.components.TimeSlotGrid
import pt.isel.courtandgo.frontend.reservations.reservationTimes.CourtAvailabilityUiState
import pt.isel.courtandgo.frontend.reservations.reservationTimes.CourtAvailabilityViewModel

@Composable
fun ChooseSlotSection(
    courtInfo: Court,
    clubInfo: Club,
    viewModel: CourtAvailabilityViewModel,
    onContinueToConfirmation: (LocalDateTime) -> Unit,
) {

    val uiState = viewModel.uiState.value

    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val selectedTime = remember { mutableStateOf<LocalTime?>(null) }
    val onlyAvailable = remember { mutableStateOf(true) }

    LaunchedEffect(selectedDate.value) {
        viewModel.loadAvailableSlots(clubInfo.id, selectedDate.value)
    }


    Column(modifier = Modifier.fillMaxSize()) {
        DatePickerRow(selectedDate.value) { selectedDate.value = it }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Mostrar apenas as horas disponíveis", modifier = Modifier.weight(1f))
            Switch(
                checked = onlyAvailable.value,
                onCheckedChange = { onlyAvailable.value = it }
            )
        }

        when (uiState) {
            is CourtAvailabilityUiState.Loading -> {
                Text(
                    text = "Carregando disponibilidade...",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CourtAvailabilityUiState.Error -> {
                Text(
                    text = "Erro: ${uiState.message}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }

            is CourtAvailabilityUiState.Success -> {

                val allTimes = uiState.availableSlots.values.flatten().distinct().sorted()

                val filteredTimes = if (selectedDate.value == currentDate) {
                    allTimes.filter { it > currentTime }
                } else {
                    allTimes
                }

                val defaultTimes = uiState.defaultTimes.sorted()

                val filteredDefaultTimes = if (selectedDate.value == currentDate) {
                    defaultTimes.filter { it > currentTime }
                } else {
                    defaultTimes
                }

                val timesToShow = if (onlyAvailable.value) {
                    filteredTimes
                } else {
                    defaultTimes
                }

                val isToday = selectedDate.value == currentDate
                val noAvailableTimesToday = onlyAvailable.value && isToday && filteredTimes.isEmpty()
                val isClubClosed = defaultTimes.isEmpty()

                if (noAvailableTimesToday) {
                    Text(
                        text = "⛔ Já não há horários disponíveis para hoje.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                if (isClubClosed) {
                    Text(
                        text = "🏴 Clube fechado neste dia.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                // Grid de horas
                TimeSlotGrid(
                    availableTimes = timesToShow,
                    selectedTime = selectedTime.value,
                    onSelect = { selectedTime.value = it },
                    isTimeEnabled = { time -> time in filteredTimes }
                )

                Button(
                    onClick = {
                        selectedTime.value?.let { time ->
                            val dateTime = LocalDateTime(selectedDate.value, time)
                            onContinueToConfirmation(dateTime)
                            selectedTime.value = null
                        }
                    },
                    enabled = selectedTime.value != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Continuar")
                }
            }
        }
    }
}






