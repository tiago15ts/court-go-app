package pt.isel.courtandgo.frontend.reservations.reservationTimes.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.SportType

@Composable
fun CourtDetailsSection(courtInfo: Court) {
    Column(modifier = Modifier.padding(16.dp)) {
        // 🔹 Nome
        Text(
            text = courtInfo.name,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 Cor por tipo de desporto
        val (label, sportColor) = when (courtInfo.sportType) {
            SportType.TENNIS -> "Ténis" to Color(0xFFDAF94D)
            SportType.PADEL -> "Padel" to Color(0xFF0077B6)
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(sportColor.copy(alpha = 0.2f))
                .border(2.dp, sportColor, RoundedCornerShape(16.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = label,
                color = Color.Gray,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (courtInfo.sportType == SportType.TENNIS) {
            Text(
                text = "Tipo de piso: ${courtInfo.surfaceType}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(
            text = "Capacidade por campo: ${courtInfo.capacity} pessoas",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
