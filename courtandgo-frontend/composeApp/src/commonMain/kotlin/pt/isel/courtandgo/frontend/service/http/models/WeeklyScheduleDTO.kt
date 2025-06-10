package pt.isel.courtandgo.frontend.service.http.models

import kotlinx.datetime.LocalTime
import pt.isel.courtandgo.frontend.domain.WeeklySchedule
import kotlinx.serialization.Serializable

@Serializable
data class WeeklyScheduleDTO(
    val scheduleId: Int,
    val courtId: Int,
    val weekDay: String,       // tipo String, ex: "MONDAY"
    val startTime: String,     // formato ex: "09:00"
    val endTime: String        // formato ex: "11:00"
) {
    fun toDomain(): WeeklySchedule = WeeklySchedule(
        scheduleId = scheduleId,
        courtId = courtId,
        weekDay = weekDay,
        startTime = LocalTime.parse(startTime),
        endTime = LocalTime.parse(endTime)
    )

    companion object {
        fun fromDomain(ws: WeeklySchedule) = WeeklyScheduleDTO(
            scheduleId = ws.scheduleId,
            courtId = ws.courtId,
            weekDay = ws.weekDay,
            startTime = ws.startTime.toString(),  // usa ISO-8601, ex: "09:00"
            endTime = ws.endTime.toString()
        )
    }
}
