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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.courtandgo.frontend.authentication.login.LoginViewModel

@Composable
fun HomeScreen(vm :LoginViewModel, //todo fix this param
               onStartReservationClick: () -> Unit,
               onLastReservationsClick: () -> Unit,
               ) {
    val scrollState = rememberScrollState()
    val name = vm.userName


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Olá${if (!name.isNullOrBlank()) ", $name 👋" else "!"}",
            style = MaterialTheme.typography.h4
        )
        ReservationCard(onStartReservation = onStartReservationClick)
        LastReservationCard(onReservations = onLastReservationsClick)
    }

}