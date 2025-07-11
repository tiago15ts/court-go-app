package pt.isel.courtandgo.frontend.utils.dateUtils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val nowTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) //LocalDateTime

fun LocalTime.plusMinutes(minutes: Int): LocalTime {
    val totalMinutes = this.hour * 60 + this.minute + minutes
    val newHour = totalMinutes / 60
    val newMinute = totalMinutes % 60
    return LocalTime(newHour, newMinute)
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