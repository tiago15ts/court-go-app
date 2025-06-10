package pt.isel.courtandgo.frontend.service.http

import io.ktor.client.HttpClient
import pt.isel.courtandgo.frontend.domain.SpecialSchedule
import pt.isel.courtandgo.frontend.domain.WeeklySchedule
import pt.isel.courtandgo.frontend.service.ScheduleCourtsService
import pt.isel.courtandgo.frontend.service.http.errors.NotFoundException
import pt.isel.courtandgo.frontend.service.http.models.SpecialScheduleDTO
import pt.isel.courtandgo.frontend.service.http.models.WeeklyScheduleDTO
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException
import pt.isel.courtandgo.frontend.service.http.utils.get

class ScheduleCourtsServiceHttp(private val client: HttpClient): ScheduleCourtsService {

    override suspend fun getWeeklySchedulesForCourt(courtId: Int): List<WeeklySchedule> {
        return try {
            val response = client.get<List<WeeklyScheduleDTO>>("/schedule/weekly/$courtId")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw NotFoundException("Schedules not found for court with ID $courtId: ${e.message}", e)
        }
    }

    override suspend fun getSpecialSchedulesForCourt(courtId: Int): List<SpecialSchedule> {
        return try{
            val response = client.get<List<SpecialScheduleDTO>>("/schedule/special/$courtId")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw NotFoundException("Special schedules not found for court with ID $courtId: ${e.message}", e)
        }
    }
}