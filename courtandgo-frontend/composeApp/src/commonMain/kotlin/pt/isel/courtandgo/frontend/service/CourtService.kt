package pt.isel.courtandgo.frontend.service

import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.SportType

interface CourtService {
    suspend fun getAllCourts(): List<Court>
    suspend fun getCourtsByDistrict(district: String): List<Court>
    suspend fun getCourtsBySport(sport: SportType): List<Court>
    suspend fun getCourtsFiltered(district: String, sport: SportType): List<Court>
    suspend fun getCourtById(id: Int): Court?
    suspend fun getCourtsByOwnerId(ownerId: Int): List<Court>
    //suspend fun getCourtsByOwnerEmail(ownerEmail: String): List<Court>
}
