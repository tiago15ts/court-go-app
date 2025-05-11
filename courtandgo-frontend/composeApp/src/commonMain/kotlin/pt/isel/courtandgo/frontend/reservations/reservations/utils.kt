package pt.isel.courtandgo.frontend.reservations.reservations

import kotlinx.datetime.LocalTime

/*
suspend fun getTimeSlotsForCourtOnDate(
    scheduleService: ScheduleCourtsService,
    courtId: Int,
    date: LocalDate
): List<LocalTime> {
    val specialSchedule = scheduleService.getSpecialSchedulesForCourt(courtId, date)
    if (specialSchedule != null) {
        if (!specialSchedule.working) return emptyList()
        return generateTimeSlots(
            LocalTime.parse(specialSchedule.startTime),
            LocalTime.parse(specialSchedule.endTime)
        )
    }

    val dayOfWeek = date.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() } // "Monday", "Tuesday", etc.
    val weeklySchedule = scheduleService.getWeeklyScheduleForCourtAndDay(courtId, dayOfWeek)
        ?: return emptyList()

    return generateTimeSlots(
        LocalTime.parse(weeklySchedule.startTime),
        LocalTime.parse(weeklySchedule.endTime)
    )
}

 */


fun LocalTime.plusMinutes(minutes: Int): LocalTime {
    val totalMinutes = this.hour * 60 + this.minute + minutes
    val newHour = totalMinutes / 60
    val newMinute = totalMinutes % 60
    return LocalTime(newHour, newMinute)
}

