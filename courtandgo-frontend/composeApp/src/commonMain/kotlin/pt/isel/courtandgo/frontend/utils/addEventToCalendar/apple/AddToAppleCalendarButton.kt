package pt.isel.courtandgo.frontend.utils.addEventToCalendar.apple

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
import courtandgo_frontend.composeapp.generated.resources.appleLogo
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import org.jetbrains.compose.resources.painterResource
import pt.isel.courtandgo.frontend.utils.addEventToCalendar.CalendarLinkOpener
import pt.isel.courtandgo.frontend.utils.addEventToCalendar.googleCalendar.generateGoogleCalendarUrl

@Composable
fun AddToAppleCalendarButton(reservationId: String, calendarOpener: CalendarLinkOpener) {
    Button(onClick = {
        val url = generateAppleCalendarUrl(reservationId)
        calendarOpener.openCalendarLink(url)
    },
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(Res.drawable.appleLogo),
            contentDescription = "Apple Calendar",
            modifier = Modifier
                .width(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Adicionar ao Apple Calendar")
    }
}
