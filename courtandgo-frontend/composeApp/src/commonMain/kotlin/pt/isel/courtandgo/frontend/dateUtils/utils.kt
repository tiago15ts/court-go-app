package pt.isel.courtandgo.frontend.dateUtils

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