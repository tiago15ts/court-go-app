package pt.isel.courtandgo.frontend.repository

import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.SportTypeCourt
import pt.isel.courtandgo.frontend.repository.interfaces.CourtRepository
import pt.isel.courtandgo.frontend.service.CourtAndGoService
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException

class CourtRepositoryImpl(
    private val courtAndGoService: CourtAndGoService
) :CourtRepository {
    override suspend fun getCourtsByClubId(clubId: Int): List<Court> {
        val courts = courtAndGoService.courtService.getCourtsByClubId(clubId)
        return courts
    }

    override suspend fun getCourtById(courtId: Int): Court {
        val court = courtAndGoService.courtService.getCourtById(courtId)
        return court ?: throw CourtAndGoException(
            "Court with id $courtId not found"
        )
    }

    override suspend fun getCourtsBySportType(sportType: SportTypeCourt): List<Court> {
        val courts = courtAndGoService.courtService.getCourtsBySportType(sportType)
        return courts
    }
}