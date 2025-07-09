package pt.isel.courtandgo.frontend.clubs.utils


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import pt.isel.courtandgo.frontend.domain.SportTypeCourt
import pt.isel.courtandgo.frontend.domain.SportsClub

@Composable
fun SportClubToggleButton(label: SportsClub, selected: Boolean, onClick: () -> Unit) {
    val backgroundColor = when {
        selected && label == SportsClub.Tennis -> Color(0xFFDAF94D) // Bola de ténis
        selected && label == SportsClub.Padel -> Color(0xFF0077B6) // Azul padel
        else -> Color.White
    }

    val textColor = if (selected) Color.Black else Color.DarkGray

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor),
        border = BorderStroke(1.dp, Color.DarkGray),
        modifier = Modifier

            .height(48.dp)
    ) {
        when (label) {
            SportsClub.Tennis -> Text("Ténis", color = textColor)
            SportsClub.Padel -> Text("Padel", color = textColor)
            SportsClub.Both -> TODO()
        }
    }
}
