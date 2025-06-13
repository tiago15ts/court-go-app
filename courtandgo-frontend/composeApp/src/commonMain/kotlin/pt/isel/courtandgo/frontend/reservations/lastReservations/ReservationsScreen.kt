package pt.isel.courtandgo.frontend.reservations.lastReservations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pt.isel.courtandgo.frontend.components.SnackbarError
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.reservations.components.ReservationCard
import pt.isel.courtandgo.frontend.reservations.components.TabButton

@Composable
fun ReservationsScreen(
    viewModel: ReservationViewModel,
    userId : Int,
    onReservationClick: (Reservation) -> Unit,
    onBack: () -> Unit, ) {
    val futureReservations by viewModel.futureReservations.collectAsState()
    val pastReservations by viewModel.pastReservations.collectAsState()
    val clubNames by viewModel.clubNames
    val uiState by viewModel.uiState.collectAsState()

    val selectedTab = remember { mutableStateOf("Futuras") }
    val reservationsToShow = if (selectedTab.value == "Futuras") futureReservations else pastReservations

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.loadReservations(userId)
    }

    Column {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar"
            )
        }

        Text(
            text = "As suas reservas",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            TabButton("Futuras", selectedTab.value == "Futuras") {
                selectedTab.value = "Futuras"
            }
            TabButton("Passadas", selectedTab.value == "Passadas") {
                selectedTab.value = "Passadas"
            }
        }

        when (uiState) {
            is ReservationUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }

            is ReservationUiState.Error -> {
                val errorMessage = (uiState as ReservationUiState.Error).message
                LaunchedEffect(errorMessage) {
                    snackbarHostState.showSnackbar(errorMessage)
                }
            }

            else -> {
                if (reservationsToShow.isEmpty()) {
                    Text(
                        text = "Ainda não existem reservas ${if (selectedTab.value == "Futuras") "futuras" else "passadas"}.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(reservationsToShow) { reservation ->
                            val clubName = clubNames[reservation.courtId] ?: "Campo desconhecido"
                            ReservationCard(
                                reservation, clubName,
                                onClick = { onReservationClick(reservation) }
                            )
                        }
                    }
                }
            }
        }
        SnackbarError(snackbarHostState)
    }
}

