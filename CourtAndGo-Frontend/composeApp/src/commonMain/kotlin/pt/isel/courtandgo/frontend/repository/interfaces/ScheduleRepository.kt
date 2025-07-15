package pt.isel.courtandgo.frontend.repository.interfaces

import pt.isel.courtandgo.frontend.domain.SpecialSchedule
import pt.isel.courtandgo.frontend.domain.WeeklySchedule

interface ScheduleRepository {
    suspend fun getWeeklySchedulesForCourt(courtId: Int): List<WeeklySchedule>
    suspend fun getSpecialSchedulesForCourt(courtId: Int): List<SpecialSchedule>
}