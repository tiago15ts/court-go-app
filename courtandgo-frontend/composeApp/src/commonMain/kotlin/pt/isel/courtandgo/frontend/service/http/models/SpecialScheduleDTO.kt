package pt.isel.courtandgo.frontend.service.http.models

import kotlinx.datetime.LocalTime
import pt.isel.courtandgo.frontend.domain.SpecialSchedule
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

@Serializable
data class SpecialScheduleDTO(
    val specialId: Int,
    val courtId: Int,
    val date: String,               // formato: "YYYY-MM-DD"
    val startTime: String?,         // formato: "HH:mm", pode ser null
    val endTime: String?,           // formato: "HH:mm", pode ser null
    val working: Boolean
) {
    fun toDomain(): SpecialSchedule = SpecialSchedule(
        specialId = specialId,
        courtId = courtId,
        date = LocalDate.parse(date),
        startTime = startTime?.let { LocalTime.parse(it) },
        endTime = endTime?.let { LocalTime.parse(it) },
        working = working
    )

    companion object {
        fun fromDomain(ss: SpecialSchedule): SpecialScheduleDTO = SpecialScheduleDTO(
            specialId = ss.specialId,
            courtId = ss.courtId,
            date = ss.date.toString(),
            startTime = ss.startTime?.toString(),
            endTime = ss.endTime?.toString(),
            working = ss.working
        )
    }
}
