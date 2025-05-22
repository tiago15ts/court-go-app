package pt.isel.courtandgo.frontend.reservations.reservationTimes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalDateTime
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.reservations.components.CourtHeader
import pt.isel.courtandgo.frontend.reservations.components.CourtTabs
import pt.isel.courtandgo.frontend.reservations.reservationTimes.sections.CourtDetailsSection
import pt.isel.courtandgo.frontend.reservations.reservationTimes.sections.ReserveCourtSection


@Composable
fun SelectedCourtScreen(
    courtInfo: Court,
    onBack: () -> Unit,
    reserveCourtViewModel: ReserveCourtViewModel,
    onContinueToConfirmation: (Court, LocalDateTime) -> Unit,
) {
    val selectedTab = remember { mutableStateOf("Reservar") }

    Column(modifier = Modifier.fillMaxSize()) {


        CourtHeader(
            courtName = courtInfo.name,
            location = courtInfo.district,
            onBack = onBack
        )

        CourtTabs(
            selectedTab = selectedTab.value,
            onTabSelected = { selectedTab.value = it }
        )

        when (selectedTab.value) {
            "Detalhes" -> {
                CourtDetailsSection(courtInfo = courtInfo)
            }

            "Reservar" -> {
                ReserveCourtSection(
                    courtInfo = courtInfo,
                    viewModel = reserveCourtViewModel,
                    onContinueToConfirmation = { court, dateTime ->
                        onContinueToConfirmation(court, dateTime)
                    }
                )
            }

            // "Torneios" -> futuro
        }
    }
}
