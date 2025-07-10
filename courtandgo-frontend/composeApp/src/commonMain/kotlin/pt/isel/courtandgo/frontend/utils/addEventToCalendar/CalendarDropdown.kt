package pt.isel.courtandgo.frontend.utils.addEventToCalendar



import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import pt.isel.courtandgo.frontend.utils.addEventToCalendar.apple.AddToAppleCalendarButton
import pt.isel.courtandgo.frontend.utils.addEventToCalendar.office.AddToOfficeCalendarButton
import pt.isel.courtandgo.frontend.utils.addEventToCalendar.outlook.AddToOutlookCalendarButton


@Composable
fun CalendarDropdown(
    reservationId: String,
    title: String,
    location: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    calendarOpener: CalendarLinkOpener
) {
    var expanded by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "arrow-rotation"
    )

    Column {
        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar ao Calendário")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = if (expanded) "Fechar" else "Abrir",
                modifier = Modifier.rotate(rotation)
            )
        }

        AnimatedVisibility(visible = expanded) {
            Column {

                AddToAppleCalendarButton(
                    reservationId = reservationId,
                    calendarOpener = calendarOpener
                )
                AddToOutlookCalendarButton(
                    title = title,
                    location = location,
                    startTime = startTime,
                    endTime = endTime,
                    calendarOpener = calendarOpener
                )
                AddToOfficeCalendarButton(
                    title = title,
                    location = location,
                    startTime = startTime,
                    endTime = endTime,
                    calendarOpener = calendarOpener
                )
            }
        }
    }
}
