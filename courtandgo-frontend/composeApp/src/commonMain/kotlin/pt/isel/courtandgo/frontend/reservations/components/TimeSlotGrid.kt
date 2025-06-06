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
import pt.isel.courtandgo.frontend.utils.dateUtils.formatToHourMinute

@Composable
fun TimeSlotGrid(
    availableTimes: List<LocalTime>,
    selectedTime: LocalTime?,
    onSelect: (LocalTime) -> Unit,
    isTimeEnabled: (LocalTime) -> Boolean = { true }
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
            val isEnabled = isTimeEnabled(time)

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        when {
                            isSelected -> Color.Black
                            !isEnabled -> Color.Gray.copy(alpha = 0.4f)
                            else -> Color.LightGray
                        }
                    )
                    .then(
                        if (isEnabled) Modifier.clickable { onSelect(time) }
                        else Modifier // desativa clique
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = time.formatToHourMinute(),
                    color = if (isEnabled) Color.White else Color.DarkGray,
                    modifier = Modifier.fillMaxWidth(),
                    // aplica riscado se indisponível
                    style = if (!isEnabled) {
                        androidx.compose.ui.text.TextStyle(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough)
                    } else {
                        androidx.compose.ui.text.TextStyle.Default
                    }
                )
            }
        }

    }
}



