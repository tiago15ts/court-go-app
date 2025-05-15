package pt.isel.courtandgo.frontend.reservations.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalTime
import pt.isel.courtandgo.frontend.dateUtils.formatToHourMinute

@Composable
fun TimeSlotGrid(
    availableTimes: List<LocalTime>,
    selectedTime: LocalTime?,
    onSelect: (LocalTime) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxHeight(0.6f)
    ) {
        items(availableTimes.size) { index ->
            val time = availableTimes[index]
            val isSelected = time == selectedTime
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) Color.Black else Color.LightGray)
                    .clickable { onSelect(time) }
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(time.formatToHourMinute(), color = Color.White)
            }
        }
    }
}



