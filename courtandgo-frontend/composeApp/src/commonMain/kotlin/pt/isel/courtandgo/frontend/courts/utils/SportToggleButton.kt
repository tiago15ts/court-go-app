package pt.isel.courtandgo.frontend.courts.utils


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun SportToggleButton(label: String, selected: Boolean, onClick: () -> Unit) {
    val backgroundColor = when {
        selected && label == "Ténis" -> Color(0xFFDAF94D) // Bola de ténis
        selected && label == "Padel" -> Color(0xFF0077B6) // Azul padel
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
        Text(label, color = textColor)
    }
}
