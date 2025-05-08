package pt.isel.courtandgo.frontend.service

import pt.isel.courtandgo.frontend.domain.SpecialSchedule
import pt.isel.courtandgo.frontend.domain.WeeklySchedule

interface ScheduleCourtsService {
    suspend fun getWeeklySchedulesForCourt(courtId: Int): List<WeeklySchedule>
    suspend fun getSpecialSchedulesForCourt(courtId: Int): List<SpecialSchedule>
}