package pt.isel.courtandgo.frontend.domain
import kotlinx.datetime.LocalTime

data class WeeklySchedule(
    val scheduleId: Int,
    val courtId: Int,
    val weekDay: String,             // ex: "MONDAY", "TUESDAY"
    val startTime: LocalTime,
    val endTime: LocalTime
)
