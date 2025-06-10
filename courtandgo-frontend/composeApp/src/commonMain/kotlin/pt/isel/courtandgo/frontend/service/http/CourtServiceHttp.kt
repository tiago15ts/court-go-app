package pt.isel.courtandgo.frontend.service.http

import io.ktor.client.HttpClient
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.service.CourtService
import pt.isel.courtandgo.frontend.service.http.errors.NotFoundException
import pt.isel.courtandgo.frontend.service.http.models.court.CourtDTO
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException
import pt.isel.courtandgo.frontend.service.http.utils.get


class CourtServiceHttp(private val client: HttpClient): CourtService {
    override suspend fun getCourtsByClubId(clubId: Int): List<Court> {
        return try {
            val response = client.get<List<CourtDTO>>("/courts/club/$clubId")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw NotFoundException(
                "Not found courts for club with ClubID $clubId : ${e.message}", e
            )
        }
    }

    override suspend fun getCourtById(courtId: Int): Court? {
        return try {
            val response = client.get<CourtDTO>("/courts/$courtId")
            response.toDomain()
        } catch (e: CourtAndGoException) {
            throw NotFoundException(
                "Court with ID $courtId not found: ${e.message}", e
            )
        }
    }

    override suspend fun getCourtsBySportType(sportType: String): List<Court> {
        return try {
            val response = client.get<List<CourtDTO>>("/courts/sport/$sportType")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw NotFoundException(
                "Not found courts for sport type $sportType: ${e.message}", e
            )
        }
    }
}