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
    val special = repo.getSpecialSchedulesForCourt(courtId)
    val weekly = repo.getWeeklySchedulesForCourt(courtId)

    val minutes = 60

    val specialForDate = special.find { it.date == date }
    if (specialForDate != null) {
        // Se o clube estiver fechado nesse dia ou horários inválidos, retorna vazio
        if (!specialForDate.working || specialForDate.startTime == null || specialForDate.endTime == null) {
            return emptyList()
        }

        return generateTimeSlots(specialForDate.startTime, specialForDate.endTime, minutes)
    }

    // Se não houver special, segue com o horário semanal
    val weeklyForDay = weekly.find { it.weekDay.equals(date.dayOfWeek.name, ignoreCase = true) }

    if (weeklyForDay?.startTime == null || weeklyForDay.endTime == null) {
        return emptyList()
    }

    return generateTimeSlots(weeklyForDay.startTime, weeklyForDay.endTime, minutes)
}

fun generateTimeSlots(start: LocalTime, end: LocalTime, stepMinutes: Int): List<LocalTime> {
    val slots = mutableListOf<LocalTime>()
    var current = start

    while (current < end) {
        slots.add(current)

        try {
            current = current.plusMinutes(stepMinutes)
        } catch (e: IllegalArgumentException) {
            break
        }

        if (stepMinutes <= 0) break
    }

    return slots
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