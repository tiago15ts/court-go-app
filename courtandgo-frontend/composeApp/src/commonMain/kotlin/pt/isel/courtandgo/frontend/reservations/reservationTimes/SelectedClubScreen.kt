package pt.isel.courtandgo.frontend.reservations.reservationTimes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalDateTime
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.reservations.components.CourtHeader
import pt.isel.courtandgo.frontend.reservations.components.CourtTabs
import pt.isel.courtandgo.frontend.reservations.reservationTimes.sections.CourtDetailsSection
import pt.isel.courtandgo.frontend.reservations.reservationTimes.sections.ChooseSlotSection


@Composable
fun SelectedClubScreen(
    clubInfo: Club,
    courtInfo: Court,
    onBack: () -> Unit,
    courtAvailabilityViewModel: CourtAvailabilityViewModel,
    onContinueToConfirmation: (LocalDateTime) -> Unit,
) {
    val selectedTab = remember { mutableStateOf("Reservar") }

    Column(modifier = Modifier.fillMaxSize()) {


        CourtHeader(
            courtName = clubInfo.name,
            location = clubInfo.location.district,
            onBack = onBack
        )

        CourtTabs(
            selectedTab = selectedTab.value,
            onTabSelected = { selectedTab.value = it }
        )

        when (selectedTab.value) {
            "Detalhes" -> {
                CourtDetailsSection(courtInfo = courtInfo, clubInfo = clubInfo)
            }

            "Reservar" -> {
                ChooseSlotSection(
                    courtInfo = courtInfo,
                    clubInfo = clubInfo,
                    viewModel = courtAvailabilityViewModel,
                    onContinueToConfirmation = { dateTime ->
                        onContinueToConfirmation(dateTime)
                    }
                )
            }

            // "Torneios" -> futuro
        }
    }
}
