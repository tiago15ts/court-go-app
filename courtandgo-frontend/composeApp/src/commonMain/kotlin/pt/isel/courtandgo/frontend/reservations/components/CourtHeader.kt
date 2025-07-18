package pt.isel.courtandgo.frontend.reservations.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.reservations.utils.getCourtImage

@Composable
fun CourtHeader(court: Court, clubName: String, location: String, onBack: () -> Unit) {
    val image = getCourtImage(court)
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ){
        Image(
            painter = painterResource(image),
            contentDescription = "Courts image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        IconButton(onClick = onBack, modifier = Modifier.padding(16.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
        }
        Column(modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(16.dp)) {
            Text(clubName, style = MaterialTheme.typography.displayMedium, color = Color.White)
            Text(location, style = MaterialTheme.typography.bodyMedium, color = Color.White)
        }
    }
}
