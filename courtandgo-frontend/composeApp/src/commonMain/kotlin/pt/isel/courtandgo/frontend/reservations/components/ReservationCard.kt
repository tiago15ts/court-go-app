package pt.isel.courtandgo.frontend.reservations.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import courtandgo_frontend.composeapp.generated.resources.Res
import courtandgo_frontend.composeapp.generated.resources.courts
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.jetbrains.compose.resources.painterResource
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus
import pt.isel.courtandgo.frontend.utils.dateUtils.formatToDisplay


@Composable
fun ReservationCard(
    reservation: Reservation,
    clubName : String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val now = Clock.System.now()
            val start = reservation.startTime.toInstant(TimeZone.currentSystemDefault())
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.courts),
                    contentDescription = "Courts image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(10.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = clubName,
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
                        text = "Preço: ${reservation.estimatedPrice} €",
                        style = MaterialTheme.typography.bodySmall
                    )

                    if (reservation.status == ReservationStatus.Cancelled) {
                        Text(
                            text = "Cancelado",
                            color = Color(0xFFB00020),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(4.dp)
                                .background(
                                    color = Color(0xFFFFEBEE),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFB00020),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }

                    val isFuture = start > now
                    if (isFuture && reservation.status == ReservationStatus.Pending) {
                        Text(
                            text = "Por Confirmar",
                            color = Color(0xFFFFA000),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(4.dp)
                                .background(
                                    color = Color(0xFFFFF8E1),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFFFA000),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }

                    if (isFuture && reservation.status == ReservationStatus.Confirmed) {
                        Text(
                            text = "Confirmado",
                            color = Color(0xFF2E7D32),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(4.dp)
                                .background(
                                    color = Color(0xFFE8F5E9),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFF2E7D32),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                val hoursUntilStart = (start - now).inWholeHours
                val showAlert = hoursUntilStart in 0..24 && reservation.status != ReservationStatus.Cancelled

                if (showAlert) {
                    Box(
                        modifier = Modifier
                            .offset(x = 4.dp)
                            .size(18.dp)
                            .background(Color.Red, RoundedCornerShape(50))
                            .border(1.dp, Color.White, RoundedCornerShape(50))
                    ) {
                        Text(
                            text = "!",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
