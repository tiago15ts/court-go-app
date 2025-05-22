package pt.isel.courtandgo.frontend.reservations.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun CourtHeader(courtName: String, location: String, onBack: () -> Unit) {
    Box {
        CoilImage(
            imageModel = {"https://americanpadelsystems.com/images/portfolio/projects-1.jpg"},
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            },
            failure = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material.Text("Ups! Não conseguimos carregar a imagem")
                }
            },
        )
        IconButton(onClick = onBack, modifier = Modifier.padding(16.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
        }
        Column(modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(16.dp)) {
            Text(courtName, style = MaterialTheme.typography.displayMedium, color = Color.White)
            Text(location, style = MaterialTheme.typography.bodyMedium, color = Color.White)
        }
    }
}
