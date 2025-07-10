package pt.isel.courtandgo.frontend.utils.addEventToCalendar.office

import io.ktor.http.encodeURLParameter
import kotlinx.datetime.LocalDateTime
import pt.isel.courtandgo.frontend.utils.dateUtils.formatToDisplay

fun generateOfficeCalendarUrl(
    title: String,
    description: String,
    location: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime
): String {
    val start = formatToDisplay(startTime)
    val end = formatToDisplay(endTime)

    return buildString {
        append("https://outlook.office.com/calendar/0/deeplink/compose?")
        append("subject=${title.encodeURLParameter()}")
        append("&body=${description.encodeURLParameter()}")
        append("&location=${location.encodeURLParameter()}")
        append("&startdt=$start")
        append("&enddt=$end")
    }
}
