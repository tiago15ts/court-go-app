package pt.isel.courtandgo.frontend.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.courtandgo.frontend.authentication.AuthUiState
import pt.isel.courtandgo.frontend.authentication.AuthViewModel

@Composable
fun HomeScreen(vm :AuthViewModel,
               onStartReservationClick: () -> Unit,
               onLastReservationsClick: () -> Unit,
               ) {
    val scrollState = rememberScrollState()
    val authState by vm.uiState.collectAsState()

    val userName = (authState as? AuthUiState.Success)?.user?.name

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Olá${if (!userName.isNullOrBlank()) ", $userName 👋" else "!"}",
            style = MaterialTheme.typography.h4
        )
        ReservationHomeCard(onStartReservation = onStartReservationClick)
        LastReservationCard(onReservations = onLastReservationsClick)
    }

}