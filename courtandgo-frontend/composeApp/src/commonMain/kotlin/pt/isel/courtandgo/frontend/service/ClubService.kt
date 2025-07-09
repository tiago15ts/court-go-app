package pt.isel.courtandgo.frontend.service

import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.SportsClub

interface ClubService {
    suspend fun getAllClubs(): List<Club>
    suspend fun getClubsByDistrict(district: String): List<Club>
    suspend fun getClubsByCounty(county: String): List<Club>
    suspend fun getClubsByCountry(country: String): List<Club>
    suspend fun getClubsByPostalCode(postalCode: String): List<Club>
    suspend fun getClubsByName(name: String): List<Club>
    suspend fun getClubsBySport(sport: SportsClub): List<Club>
    //suspend fun getClubsFiltered(district: String, sport: SportType): List<Club>
    suspend fun getClubById(id: Int): Club?
    suspend fun getClubsByOwnerId(ownerId: Int): List<Club>
    suspend fun getClubIdByCourtId(courtId: Int): Int

    suspend fun getClubsFiltered(
        query: String? = null,
        county: String? = null,
        district: String? = null,
        country: String? = null,
        postalCode: String? = null,
        sport: SportsClub
    ): List<Club>
}
