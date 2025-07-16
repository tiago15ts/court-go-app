package pt.isel.courtandgo.frontend.utils.dateUtils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val nowTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) //LocalDateTime

fun LocalTime.plusMinutes(minutes: Int): LocalTime {
    val totalMinutes = this.hour * 60 + this.minute + minutes
    if (totalMinutes >= 24 * 60) {
        throw IllegalArgumentException("LocalTime cannot exceed 23:59")
    }
    val hours = totalMinutes / 60
    val mins = totalMinutes % 60
    return LocalTime(hours, mins)
}


val currentDate = nowTime.date //localDate
val currentTime = nowTime.time //localTime

val timeZone = TimeZone.currentSystemDefault() //TimeZone

val diasSemanaPT = mapOf(
    "MONDAY" to "Seg.",
    "TUESDAY" to "Ter.",
    "WEDNESDAY" to "Qua.",
    "THURSDAY" to "Qui.",
    "FRIDAY" to "Sex.",
    "SATURDAY" to "Sáb.",
    "SUNDAY" to "Dom."
)