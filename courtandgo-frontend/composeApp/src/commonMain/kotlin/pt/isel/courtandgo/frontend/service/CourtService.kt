package pt.isel.courtandgo.frontend.service

import pt.isel.courtandgo.frontend.domain.Court

interface CourtService {
    suspend fun getCourtsByClubId(clubId: Int): List<Court>
    suspend fun getCourtById(courtId: Int): Court?
    suspend fun getCourtsBySportType(sportType: String): List<Court>
    // maybe add more methods as needed
}