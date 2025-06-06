package pt.isel.courtandgo.frontend.dateUtils

import android.content.Intent
import android.provider.CalendarContract
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

/*
actual fun addEventToCalendar(
    reservationDateTime: LocalDateTime,
    timeZone: TimeZone,
    title: String
) {
    val instant = reservationDateTime.toInstant(timeZone)
    val startMillis = instant.toEpochMilliseconds()

    val intent = Intent(Intent.ACTION_INSERT).apply {
        data = CalendarContract.Events.CONTENT_URI
        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startMillis + 60 * 60 * 1000)
        putExtra(CalendarContract.Events.TITLE, title)
        putExtra(CalendarContract.Events.DESCRIPTION, "Reserva feita na app CourtAndGo")
    }

    appCalendarContext.startActivity(intent)
}

 */
