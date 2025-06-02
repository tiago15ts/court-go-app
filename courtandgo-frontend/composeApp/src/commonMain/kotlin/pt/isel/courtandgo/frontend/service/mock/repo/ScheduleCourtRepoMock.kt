package pt.isel.courtandgo.frontend.service.mock.repo

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import pt.isel.courtandgo.frontend.domain.SpecialSchedule
import pt.isel.courtandgo.frontend.domain.WeeklySchedule

class ScheduleCourtRepoMock {
    private val weeklySchedules = mutableListOf<WeeklySchedule>(
        WeeklySchedule(1, 1, "Monday", LocalTime(9,0), LocalTime(22,0)),
        WeeklySchedule(2, 1, "Tuesday", LocalTime(8,0), LocalTime(22,0)),
        WeeklySchedule(3, 1, "Wednesday", LocalTime(9,0), LocalTime(22,0)),
        WeeklySchedule(4, 1, "Thursday", LocalTime(9,0), LocalTime(22,0)),
        WeeklySchedule(5, 1, "Friday", LocalTime(9,0), LocalTime(22,30)),
        WeeklySchedule(6, 1, "Saturday", LocalTime(10,0), LocalTime(19,0)),
        WeeklySchedule(7, 1, "Sunday", LocalTime(11,0), LocalTime(18,0)),
        WeeklySchedule(8, 2, "Monday", LocalTime(9,0), LocalTime(22,0)),
        WeeklySchedule(9, 2, "Tuesday", LocalTime(8,0), LocalTime(22,0)),
        WeeklySchedule(10, 2, "Wednesday", LocalTime(9,0), LocalTime(22,0)),
        WeeklySchedule(11, 2, "Thursday", LocalTime(9,0), LocalTime(22,0)),
        WeeklySchedule(12, 2, "Friday", LocalTime(9,0), LocalTime(22,30)),

    )
    private val specialSchedules = mutableListOf<SpecialSchedule>(
        SpecialSchedule(1, 1, LocalDate(2025, 5, 9), null, null, false)
    )

    fun getWeeklySchedulesForCourt(courtId: Int): List<WeeklySchedule> {
        return weeklySchedules.filter { it.courtId == courtId }
    }

    fun getSpecialSchedulesForCourt(courtId: Int): List<SpecialSchedule> {
        return specialSchedules.filter { it.courtId == courtId }
    }
}