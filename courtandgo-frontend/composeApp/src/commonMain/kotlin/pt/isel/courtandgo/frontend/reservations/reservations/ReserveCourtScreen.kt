package pt.isel.courtandgo.frontend.reservations.reservations

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
import kotlinx.datetime.LocalTime
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.reservations.components.CourtHeaderSection
import pt.isel.courtandgo.frontend.reservations.components.CourtTabs
import pt.isel.courtandgo.frontend.dateUtils.DatePickerRow
import pt.isel.courtandgo.frontend.reservations.components.TimeSlotGrid

@Composable
fun ReserveCourtScreen(
    courtInfo: Court,
    onBack: () -> Unit,
    viewModel: ReserveCourtViewModel,
    ) {
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val selectedTime = remember { mutableStateOf<LocalTime?>(null) }
    val onlyAvailable = remember { mutableStateOf(true) }
    val availableTimes = viewModel.availableTimes.value

    LaunchedEffect(selectedDate.value) {
        viewModel.loadTimesForDate(courtInfo.id, selectedDate.value)
    }


    Column(modifier = Modifier.fillMaxSize()) {
        // Foto e nome do court
        CourtHeaderSection(courtName = courtInfo.name, location = courtInfo.district, onBack = onBack)

        // Tabs: Detalhes | Reservar | Open Matches | Torneios (apenas mockados)
        val selectedTab = remember { mutableStateOf("Reservar") }

        CourtTabs(
            selectedTab = selectedTab.value,
            onTabSelected = { selectedTab.value = it }
        )


        // Seletor de data horizontal (dias da semana)
        DatePickerRow(selectedDate.value) { selectedDate.value = it }

        // Mostrar apenas disponíveis toggle
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

        // Grid de horas
        TimeSlotGrid(
            availableTimes = availableTimes,
            selectedTime = selectedTime.value,
            onSelect = { selectedTime.value = it }
        )

        // Botão para continuar (ativo só com hora selecionada)
        Button(
            onClick = { /* todo ir para confirmação de uma reserva*/ },
            enabled = selectedTime.value != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Confirmar Reserva")
        }
    }
}






