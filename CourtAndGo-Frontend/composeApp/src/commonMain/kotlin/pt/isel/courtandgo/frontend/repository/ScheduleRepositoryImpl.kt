package pt.isel.courtandgo.frontend.repository

import pt.isel.courtandgo.frontend.domain.SpecialSchedule
import pt.isel.courtandgo.frontend.domain.WeeklySchedule
import pt.isel.courtandgo.frontend.repository.interfaces.ScheduleRepository
import pt.isel.courtandgo.frontend.service.CourtAndGoService

class ScheduleRepositoryImpl(
    private val courtAndGoService: CourtAndGoService
) : ScheduleRepository {
    override suspend fun getWeeklySchedulesForCourt(courtId: Int): List<WeeklySchedule> {
        val schedules = courtAndGoService.scheduleCourtsService.getWeeklySchedulesForCourt(courtId)
        return schedules
    }

    override suspend fun getSpecialSchedulesForCourt(courtId: Int): List<SpecialSchedule> {
        val schedules = courtAndGoService.scheduleCourtsService.getSpecialSchedulesForCourt(courtId)
        return schedules
    }
}