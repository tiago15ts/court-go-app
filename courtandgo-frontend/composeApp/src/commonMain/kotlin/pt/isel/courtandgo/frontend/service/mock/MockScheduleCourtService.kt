package pt.isel.courtandgo.frontend.service.mock

import pt.isel.courtandgo.frontend.domain.SpecialSchedule
import pt.isel.courtandgo.frontend.domain.WeeklySchedule
import pt.isel.courtandgo.frontend.service.ScheduleCourtsService
import pt.isel.courtandgo.frontend.service.mock.repo.ScheduleCourtRepoMock

class MockScheduleCourtService(private val repoMock : ScheduleCourtRepoMock) :
    ScheduleCourtsService {

    override suspend fun getWeeklySchedulesForCourt(courtId: Int): List<WeeklySchedule> {
        return repoMock.getWeeklySchedulesForCourt(courtId)
    }

    override suspend fun getSpecialSchedulesForCourt(courtId: Int): List<SpecialSchedule> {
        return repoMock.getSpecialSchedulesForCourt(courtId)
    }
}