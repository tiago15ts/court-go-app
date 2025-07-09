package pt.isel.courtandgo.frontend.reservations.lastReservations.reservationDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.raedghazal.kotlinx_datetime_ext.plus
import courtandgo_frontend.composeapp.generated.resources.Res
import courtandgo_frontend.composeapp.generated.resources.courts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import org.jetbrains.compose.resources.painterResource
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus
import pt.isel.courtandgo.frontend.ui.greenConfirmation
import pt.isel.courtandgo.frontend.utils.addEventToCalendar.AddToCalendarButton
import pt.isel.courtandgo.frontend.utils.dateUtils.CalendarLinkOpener
import pt.isel.courtandgo.frontend.utils.dateUtils.formatToDisplay
import pt.isel.courtandgo.frontend.utils.dateUtils.nowTime
import pt.isel.courtandgo.frontend.utils.dateUtils.timeZone
import pt.isel.courtandgo.frontend.utils.formatLocationForDisplay
import pt.isel.courtandgo.frontend.utils.toPortugueseName


@Composable
fun ReservationDetailsScreen(
    reservation: Reservation,
    clubInfo : Club,
    courtInfo : Court,
    calendarOpener: CalendarLinkOpener,
    onBack: () -> Unit,
    onConfirmReservation: (Reservation) -> Unit,
    onCancelReservation: (Reservation) -> Unit
) {
    val now = nowTime
    val start = reservation.startTime

    val isFuture = start > now
    val canCancel = isFuture && reservation.status != ReservationStatus.Cancelled
    val canStillCancel = start > now.plus(1, DateTimeUnit.HOUR)
    val isConfirmed = reservation.status == ReservationStatus.Confirmed

    val isConfirming = remember { mutableStateOf(false) }
    val isCancelling = remember { mutableStateOf(false) }


    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
    ) {
        TextButton(onClick = onBack) {
            Text("← Voltar", style = MaterialTheme.typography.labelLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(Res.drawable.courts),
            contentDescription = "Courts image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Detalhes da Reserva", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        Text("ID da Reserva: ${reservation.id}")
        Text("Clube: ${clubInfo.name}")
        Text("Campo: ${courtInfo.name}")
        Text("Desporto: ${courtInfo.sportTypeCourt.toPortugueseName()}")
        Text("Início: ${formatToDisplay(reservation.startTime)}")
        Text("Fim: ${formatToDisplay(reservation.endTime)}")
        Text("Preço Estimado: ${reservation.estimatedPrice} €")
        Text("Estado: ${reservation.status.name.lowercase().replaceFirstChar { it.uppercase() }}")

        Spacer(modifier = Modifier.height(24.dp))

        val coroutineScope = rememberCoroutineScope()


        if (canCancel) {
            if (!isConfirmed) {
                Button(
                    onClick = {
                        isConfirming.value = true
                        coroutineScope.launch {
                            delay(700)
                            onConfirmReservation(reservation)
                            isConfirming.value = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32) // Verde mais escuro
                    )
                ) {
                    if (isConfirming.value) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .height(16.dp)
                                .width(16.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                        Text("A confirmar...")
                    } else {
                        Text("Confirmar Reserva")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                Text(
                    text = "✅ A sua reserva já está confirmada.",
                    color = greenConfirmation,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (canStillCancel) {
                Text(
                    "Poderá cancelar a reserva até 1 hora antes do início da mesma.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Button(
                    onClick = {
                        isCancelling.value = true
                        coroutineScope.launch {
                            delay(700)
                            onCancelReservation(reservation)
                            isCancelling.value = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    if (isCancelling.value) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .height(16.dp)
                                .width(16.dp),
                            color = MaterialTheme.colorScheme.onError,
                            strokeWidth = 2.dp
                        )
                        Text("A cancelar...", color = MaterialTheme.colorScheme.onError)
                    } else {
                        Text("Cancelar Reserva", color = MaterialTheme.colorScheme.onError)
                    }
                }
            } else {
                Text(
                    "⏳ Já não é possível cancelar esta reserva (menos de 1 hora).",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        if (reservation.status != ReservationStatus.Cancelled && isFuture) {
            AddToCalendarButton(
                title = "Reserva CourtAndGo - ${courtInfo.name} no ${clubInfo.name}",
                description = "Reserva feita na app CourtAndGo",
                location = formatLocationForDisplay(clubInfo.location),
                startTime = reservation.startTime,
                endTime = reservation.endTime,
                calendarOpener = calendarOpener,
                timeZone = timeZone,
            )
        }
    }
}
