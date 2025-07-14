package pt.isel.courtandgo.frontend.service

import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.SportTypeCourt

interface CourtService {
    suspend fun getCourtsByClubId(clubId: Int): List<Court>
    suspend fun getCourtById(courtId: Int): Court?
    suspend fun getCourtsBySportType(sportType: SportTypeCourt): List<Court>
    // maybe add more methods as needed
}