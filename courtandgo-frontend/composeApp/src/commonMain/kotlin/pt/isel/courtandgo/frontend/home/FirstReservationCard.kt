package pt.isel.courtandgo.frontend.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun FirstReservationCard(onStartReservation:() -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(16.dp)
            .width(400.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                        Text("Ups! Não conseguimos carregar a imagem")
                    }
                },
            )


            Spacer(modifier = Modifier.height(16.dp))

            Text("Faça a sua primeira reserva", style = MaterialTheme.typography.h6)
            Text(
                "Escolha o campo mais próximo de si.",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onStartReservation,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF4CAF50)
                ),
            ) {
                Text("Começar")
            }
        }
    }
}