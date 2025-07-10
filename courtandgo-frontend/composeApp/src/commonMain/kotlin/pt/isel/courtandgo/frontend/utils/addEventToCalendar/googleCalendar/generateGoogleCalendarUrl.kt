package pt.isel.courtandgo.frontend.utils.addEventToCalendar.googleCalendar

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

fun generateGoogleCalendarUrl(
    title: String,
    description: String,
    location: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    timeZone: TimeZone
): String {
    val startUtc = startTime.toInstant(timeZone)
        .toString() // e.g., 2025-06-07T14:00:00Z
        .replace("-", "")
        .replace(":", "")
        .replace("Z", "")

    val endUtc = endTime.toInstant(timeZone)
        .toString()
        .replace("-", "")
        .replace(":", "")
        .replace("Z", "")

    return buildString {
        append("https://calendar.google.com/calendar/render?action=TEMPLATE")
        append("&text=${urlEncode(title)}")
        append("&dates=${startUtc}/${endUtc}")
        append("&details=${urlEncode(description)}")
        append("&location=${urlEncode(location)}")
    }
}

// Função simples de URL encode (substitui espaços, etc.)
fun urlEncode(str: String): String =
    str.encodeToByteArray()
        .joinToString("") {
            val c = it.toInt()
            when (c) {
                in 'A'.code..'Z'.code,
                in 'a'.code..'z'.code,
                in '0'.code..'9'.code -> it.toInt().toChar().toString()
                ' '.code -> "+"
                else -> "%${toHex(c)}"
            }
        }

fun toHex(byte: Int): String {
    val hex = "0123456789ABCDEF"
    val high = (byte shr 4) and 0xF
    val low = byte and 0xF
    return "${hex[high]}${hex[low]}"
}

