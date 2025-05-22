package pt.isel.courtandgo.frontend.dateUtils

import kotlinx.datetime.LocalTime

fun formatToDisplay(dateTime: kotlinx.datetime.LocalDateTime): String {
    val day = dateTime.dayOfMonth.toString().padStart(2, '0')
    val month = dateTime.monthNumber.toString().padStart(2, '0')
    val year = dateTime.year
    val hour = dateTime.hour.toString().padStart(2, '0')
    val minute = dateTime.minute.toString().padStart(2, '0')
    return "$day/$month/$year às $hour:$minute"
}

fun LocalTime.formatToHourMinute(): String {
    val hourStr = if (this.hour < 10) "0${this.hour}" else "${this.hour}"
    val minuteStr = if (this.minute < 10) "0${this.minute}" else "${this.minute}"
    return "$hourStr:$minuteStr"
}

fun formatTimeToHHmm(time: LocalTime): String {
    val hour = time.hour.toString().padStart(2, '0')
    val minute = time.minute.toString().padStart(2, '0')
    return "$hour:$minute"
}

