package pt.isel.courtandgo.frontend.reservations.reservationTimes.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import pt.isel.courtandgo.frontend.dateUtils.DatePickerRow
import pt.isel.courtandgo.frontend.dateUtils.currentDate
import pt.isel.courtandgo.frontend.dateUtils.currentTime
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.reservations.components.TimeSlotGrid
import pt.isel.courtandgo.frontend.reservations.reservationTimes.CourtAvailabilityViewModel

@Composable
fun ChooseSlotSection(
    courtInfo: Court,
    clubInfo: Club,
    viewModel: CourtAvailabilityViewModel,
    onContinueToConfirmation: (LocalDateTime) -> Unit,
    ) {
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val selectedTime = remember { mutableStateOf<LocalTime?>(null) }
    val onlyAvailable = remember { mutableStateOf(true) }
    val availableTimes = viewModel.availableSlots.value

    LaunchedEffect(selectedDate.value) {
        viewModel.loadAvailableSlots(courtInfo.id, selectedDate.value)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Seletor de data horizontal (dias da semana)
        DatePickerRow(selectedDate.value) { selectedDate.value = it }

        // todo mostrar apenas disponíveis toggle
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

        val filteredTimes = if (selectedDate.value == currentDate) {
            availableTimes.filter { it > currentTime }
        } else {
            availableTimes
        }
        // Grid de horas
        TimeSlotGrid(
            availableTimes = filteredTimes,
            selectedTime = selectedTime.value,
            onSelect = { selectedTime.value = it }
        )

        Button(
            onClick = {
                selectedTime.value?.let { time ->
                    val dateTime = LocalDateTime(selectedDate.value, time)
                    onContinueToConfirmation(dateTime)
                    selectedTime.value = null // Limpa a seleção após continuar
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






