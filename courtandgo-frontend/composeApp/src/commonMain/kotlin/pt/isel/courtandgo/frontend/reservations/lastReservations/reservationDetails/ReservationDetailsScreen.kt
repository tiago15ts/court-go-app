package pt.isel.courtandgo.frontend.reservations.lastReservations.reservationDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.raedghazal.kotlinx_datetime_ext.plus
import com.skydoves.landscapist.coil3.CoilImage
import kotlinx.datetime.DateTimeUnit
import pt.isel.courtandgo.frontend.dateUtils.formatToDisplay
import pt.isel.courtandgo.frontend.dateUtils.nowTime
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus

@Composable
fun ReservationDetailsScreen(
    reservation: Reservation,
    //clubInfo : Club,
    //courtInfo : Court,
    onBack: () -> Unit,
    onConfirmReservation: (Reservation) -> Unit,
    onCancelReservation: (Reservation) -> Unit
) {
    val now = nowTime
    val start = reservation.startTime

    val isFuture = start > now
    val canCancel = isFuture && reservation.status != ReservationStatus.CANCELLED
    val canStillCancel = start > now.plus(1, DateTimeUnit.HOUR)
    val isConfirmed = reservation.status == ReservationStatus.CONFIRMED

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
            if (!isConfirmed) {
                Button(
                    onClick = { onConfirmReservation(reservation) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32) // Verde mais escuro
                    )
                ) {
                    Text("Confirmar Reserva")
                }
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                Text(
                    text = "✅ A sua reserva já está confirmada.",
                    color = Color(0xFF2E7D32),
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
                    onClick = { onCancelReservation(reservation) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Cancelar Reserva", color = MaterialTheme.colorScheme.onError)
                }
            } else {
                Text(
                    "⏳ Já não é possível cancelar esta reserva (menos de 1 hora).",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}
