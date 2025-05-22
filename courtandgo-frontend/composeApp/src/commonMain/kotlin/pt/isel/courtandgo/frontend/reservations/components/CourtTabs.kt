package pt.isel.courtandgo.frontend.reservations.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CourtTabs(
    selectedTab: String,
    onTabSelected: (String) -> Unit = {}
) {
    val tabs = listOf("Detalhes", "Reservar")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEach { tab ->
            val isSelected = tab == selectedTab
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) Color.Black else Color.LightGray)
                    .clickable { onTabSelected(tab) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab,
                    color = if (isSelected) Color.White else Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
