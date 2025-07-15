package pt.isel.courtandgo.frontend.repository.interfaces

import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.SportTypeCourt

interface CourtRepository {
    suspend fun getCourtsByClubId(clubId: Int): List<Court>
    suspend fun getCourtById(courtId: Int): Court?
    suspend fun getCourtsBySportType(sportType: SportTypeCourt): List<Court>
}