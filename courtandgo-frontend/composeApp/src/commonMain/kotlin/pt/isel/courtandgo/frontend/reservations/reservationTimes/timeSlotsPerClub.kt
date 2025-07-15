package pt.isel.courtandgo.frontend.reservations.reservationTimes

import kotlinx.datetime.*
import pt.isel.courtandgo.frontend.utils.dateUtils.plusMinutes
import pt.isel.courtandgo.frontend.domain.SpecialSchedule
import pt.isel.courtandgo.frontend.domain.WeeklySchedule
import pt.isel.courtandgo.frontend.repository.interfaces.ScheduleRepository

suspend fun getDefaultSlotsForCourt(
    repo: ScheduleRepository,
    courtId: Int,
    date: LocalDate
): List<LocalTime> {
    val weekly = repo.getWeeklySchedulesForCourt(courtId)
    val special = repo.getSpecialSchedulesForCourt(courtId)

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

fun getAvailableTimeSlotsForClub(
    timeSlotsByCourt: Map<Int, List<LocalTime>>,
    occupiedTimesByCourt: Map<Int, List<LocalTime>>
): Map<Int, List<LocalTime>> {
    return timeSlotsByCourt.mapValues { (courtId, slots) ->
        val occupied = occupiedTimesByCourt[courtId] ?: emptyList()
        slots.filterNot { it in occupied }
    }
}