package pt.isel.courtandgo.frontend.reservations.reservations

import kotlinx.datetime.*
import pt.isel.courtandgo.frontend.domain.SpecialSchedule
import pt.isel.courtandgo.frontend.domain.WeeklySchedule
import pt.isel.courtandgo.frontend.service.ScheduleCourtsService

suspend fun getTimeSlotsForCourt(
    service: ScheduleCourtsService,
    courtId: Int,
    date: LocalDate
): List<LocalTime> {
    val weekly = service.getWeeklySchedulesForCourt(courtId)
    val special = service.getSpecialSchedulesForCourt(courtId)

    val specialForDate = special.find { it.date == date }
    val schedule = specialForDate ?: weekly.find { it.weekDay.equals(date.dayOfWeek.name, ignoreCase = true) }

    val minutes = 60

    return when (schedule) {
        is SpecialSchedule -> {
            if (!schedule.working || schedule.startTime == null || schedule.endTime == null) return emptyList()
            generateTimeSlots(schedule.startTime, schedule.endTime, minutes)
        }
        is WeeklySchedule -> {
            generateTimeSlots(schedule.startTime, schedule.endTime, minutes)
        }
        else -> emptyList()
    }

}

fun generateTimeSlots(start: LocalTime, end: LocalTime, stepMinutes: Int): List<LocalTime> {
    val slots = mutableListOf<LocalTime>()
    var current = start
    while (current < end) {
        slots.add(current)
        current = current.plusMinutes(stepMinutes)
    }
    return slots
}




fun getMockedTimeSlots(): List<LocalTime> {
    return generateSequence(LocalTime(hour = 9, minute = 0)) {
        val nextMinute = it.minute + 30
        val nextHour = it.hour + nextMinute / 60
        val adjustedMinute = nextMinute % 60

        if (nextHour > 18 || (nextHour == 18 && adjustedMinute > 30)) {
            null
        } else {
            LocalTime(hour = nextHour, minute = adjustedMinute)
        }
    }.toList()
}