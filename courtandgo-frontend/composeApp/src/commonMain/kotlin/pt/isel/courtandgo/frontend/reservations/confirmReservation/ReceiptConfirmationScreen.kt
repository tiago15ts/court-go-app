package pt.isel.courtandgo.frontend.reservations.confirmReservation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage
import pt.isel.courtandgo.frontend.dateUtils.formatToDisplay
import pt.isel.courtandgo.frontend.domain.Reservation

@Composable
fun ReceiptReservationScreen(reservation: Reservation) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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

        Text("Pode confirmar a sua reserva a partir de agora, na sua aba de reservas futuras.")
    }
}
