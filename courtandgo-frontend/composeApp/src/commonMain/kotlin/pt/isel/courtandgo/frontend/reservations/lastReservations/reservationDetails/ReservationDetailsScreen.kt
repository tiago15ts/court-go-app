package pt.isel.courtandgo.frontend.reservations.lastReservations.reservationDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage
import pt.isel.courtandgo.frontend.dateUtils.formatToDisplay
import pt.isel.courtandgo.frontend.dateUtils.nowTime
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus

@Composable
fun ReservationDetailsScreen(
    reservation: Reservation,
    onBack: () -> Unit,
    onConfirmReservation: (Reservation) -> Unit,
    onCancelReservation: (Reservation) -> Unit
) {
    val now = remember { nowTime }
    val isFuture = remember(reservation.startTime) {
        reservation.startTime > now
    }
    val canCancel = isFuture && reservation.status != ReservationStatus.CANCELLED

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("← Voltar", style = MaterialTheme.typography.labelLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        CoilImage(
            imageModel = { "https://americanpadelsystems.com/images/portfolio/projects-1.jpg" },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp)),
            loading = {
                CircularProgressIndicator()
            },
            failure = {
                Text("Erro ao carregar imagem")
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Detalhes da Reserva", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        Text("ID da Reserva: ${reservation.id}")
        Text("Início: ${formatToDisplay(reservation.startTime)}")
        Text("Fim: ${formatToDisplay(reservation.endTime)}")
        Text("Preço Estimado: ${reservation.estimatedPrice} €")
        Text("Estado: ${reservation.status.name.lowercase().replaceFirstChar { it.uppercase() }}")

        Spacer(modifier = Modifier.height(24.dp))

        if (canCancel) {
            Button(
                onClick = { onConfirmReservation(reservation) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ){
                Text("Confirmar Reserva", color = MaterialTheme.colorScheme.onPrimary)
            }
            Text(
                "Poderá cancelar a reserva até 1 hora antes do início da mesma.",
                style = MaterialTheme.typography.bodyMedium
            )
            Button(
                onClick = { onCancelReservation(reservation) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Cancelar Reserva", color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}
