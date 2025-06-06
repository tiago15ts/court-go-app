package pt.isel.courtandgo.frontend.utils.dateUtils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus


@Composable
fun DatePickerRow(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val today = nowTime.date
    val dates = (0..6).map { today.plus(it.toInt(), DateTimeUnit.DAY) }

    LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
        items(dates) { date ->
            val isSelected = date == selectedDate
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDateSelected(date) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(date.dayOfWeek.name.take(3).lowercase().replaceFirstChar { it.uppercase() })
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color.Black else Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(date.dayOfMonth.toString(), color = Color.White)
                }
            }
        }
    }
}



