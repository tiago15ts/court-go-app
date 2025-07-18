package pt.isel.courtandgo.frontend.components.datePicker

import kotlinx.datetime.LocalDate

fun LocalDate.formatToDisplay(): String {
    val day = dayOfMonth.toString().padStart(2, '0')
    val month = monthNumber.toString().padStart(2, '0')
    return "$day/$month/$year"
}

fun parseDisplayDate(dateString: String): LocalDate? {
    return try {
        LocalDate.parse(dateString) // "yyyy-MM-dd"
    } catch (e1: Exception) {
        try {
            val parts = dateString.split("/")
            if (parts.size == 3) {
                val day = parts[0].toInt()
                val month = parts[1].toInt()
                val year = parts[2].toInt()
                LocalDate(year, month, day)
            } else null
        } catch (e2: Exception) {
            null
        }
    }
}

