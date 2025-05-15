package pt.isel.courtandgo.frontend.reservations.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import pt.isel.courtandgo.frontend.dateUtils.formatToDisplay
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus
import com.skydoves.landscapist.coil3.CoilImage


@Composable
fun ReservationCard(
    reservation: Reservation,
    courtName : String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                imageModel = { "https://americanpadelsystems.com/images/portfolio/projects-1.jpg" },
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(10.dp)),
                loading = {
                    CircularProgressIndicator()
                },
                failure = {
                    Text("Erro ao carregar imagem")
                },
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = courtName,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Início: ${formatToDisplay(reservation.startTime)}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Fim: ${formatToDisplay(reservation.endTime)}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Preço estimado: ${reservation.estimatedPrice} €",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "Estado: ${
                        reservation.status.name.lowercase().replaceFirstChar { it.uppercase() }
                    }",
                    style = MaterialTheme.typography.bodySmall,
                    color = when (reservation.status) {
                        ReservationStatus.CONFIRMED -> MaterialTheme.colorScheme.primary
                        ReservationStatus.CANCELLED -> MaterialTheme.colorScheme.error
                        ReservationStatus.PENDING -> MaterialTheme.colorScheme.secondary
                    }
                )
            }
        }
    }
}
