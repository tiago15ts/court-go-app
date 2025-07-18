package pt.isel.courtandgo.frontend.clubs.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.painterResource
import pt.isel.courtandgo.frontend.domain.SportsClub
import pt.isel.courtandgo.frontend.utils.dateUtils.formatTimeToHHmm


@Composable
fun ClubCard(
    name: String,
    county: String,
    district: String,
    price: String,
    hours: List<LocalTime>,
    onClick: () -> Unit,
    sportsClub: SportsClub
) {

    val image = getRandomImageForSportType(sportsClub)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row {
                Image(
                    painter = painterResource(image),
                    contentDescription = "Court image",
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                ) {
                    Text(text = name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "$county, $district", style = MaterialTheme.typography.bodySmall)

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        //Text(text = "Distância Soon", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Desde $price€", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                hours.forEach { time ->
                    val formatted = formatTimeToHHmm(time)
                    Text(
                        text = formatted,
                        modifier = Modifier
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    )
                }
            }
        }
    }
}



