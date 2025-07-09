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
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.SportTypeCourt
import pt.isel.courtandgo.frontend.ui.padelColor
import pt.isel.courtandgo.frontend.ui.tennisColor

@Composable
fun CourtDetailsSection(courtInfo: Court, clubInfo: Club) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = clubInfo.name,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Cor por tipo de desporto
        val (label, sportColor) = when (courtInfo.sportTypeCourt) {
            SportTypeCourt.Tennis -> "Ténis" to tennisColor
            SportTypeCourt.Padel -> "Padel" to padelColor
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


        if (courtInfo.sportTypeCourt == SportTypeCourt.Tennis) {
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

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "O clube tem ${clubInfo.nrOfCourts} campos disponíveis.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Localização: ${clubInfo.location.address}, ${clubInfo.location.county}, ${clubInfo.location.district.name}, ${clubInfo.location.postalCode}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
