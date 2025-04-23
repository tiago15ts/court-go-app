package pt.isel.courtandgo.frontend.components.datePicker

import kotlinx.datetime.LocalDate

fun LocalDate.formatToDisplay(): String {
    val day = dayOfMonth.toString().padStart(2, '0')
    val month = monthNumber.toString().padStart(2, '0')
    return "$day/$month/$year"
}

fun parseDisplayDate(dateString: String): LocalDate? {
    val parts = dateString.split("/")
    return if (parts.size == 3) {
        try {
            val day = parts[0].toInt()
            val month = parts[1].toInt()
            val year = parts[2].toInt()
            LocalDate(year, month, day)
        } catch (e: Exception) {
            null
        }
    } else null
}
