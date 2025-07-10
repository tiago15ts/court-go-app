package pt.isel.courtandgo.frontend.utils.addEventToCalendar.office

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import courtandgo_frontend.composeapp.generated.resources.Res
import courtandgo_frontend.composeapp.generated.resources.officeLogo
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import org.jetbrains.compose.resources.painterResource
import pt.isel.courtandgo.frontend.utils.addEventToCalendar.CalendarLinkOpener
import pt.isel.courtandgo.frontend.utils.addEventToCalendar.googleCalendar.generateGoogleCalendarUrl

@Composable
fun AddToOfficeCalendarButton(
    title: String,
    description: String = "Reserva feita na app CourtAndGo",
    location: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    calendarOpener: CalendarLinkOpener,
    timeZone: TimeZone = pt.isel.courtandgo.frontend.utils.dateUtils.timeZone
) {
    Button(
        onClick = {
            val calendarUrl = generateOfficeCalendarUrl(
                title = title,
                description = description,
                location = location,
                startTime = startTime,
                endTime = endTime
            )
            calendarOpener.openCalendarLink(calendarUrl)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(Res.drawable.officeLogo),
            contentDescription = "Office Calendar",
            modifier = Modifier
                .width(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Adicionar ao Calendário do Office")
    }
}