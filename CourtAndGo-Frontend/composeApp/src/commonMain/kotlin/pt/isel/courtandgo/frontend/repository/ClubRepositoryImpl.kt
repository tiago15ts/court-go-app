package pt.isel.courtandgo.frontend.repository

import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.SportsClub
import pt.isel.courtandgo.frontend.repository.interfaces.ClubRepository
import pt.isel.courtandgo.frontend.service.CourtAndGoService
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException

class ClubRepositoryImpl (
    private val courtAndGoService: CourtAndGoService
) : ClubRepository {
    override suspend fun getAllClubs(): List<Club> {
        val clubs = courtAndGoService.clubService.getAllClubs()
        return clubs
    }

    override suspend fun getClubsByDistrict(district: String): List<Club> {
        val clubs = courtAndGoService.clubService.getClubsByDistrict(district)
        return clubs
    }

    override suspend fun getClubsByCounty(county: String): List<Club> {
        val clubs = courtAndGoService.clubService.getClubsByCounty(county)
        return clubs
    }

    override suspend fun getClubsByCountry(country: String): List<Club> {
        val clubs = courtAndGoService.clubService.getClubsByCountry(country)
        return clubs
    }

    override suspend fun getClubsByPostalCode(postalCode: String): List<Club> {
        val clubs = courtAndGoService.clubService.getClubsByPostalCode(postalCode)
        return clubs
    }

    override suspend fun getClubsByName(name: String): List<Club> {
        val clubs = courtAndGoService.clubService.getClubsByName(name)
        return clubs
    }

    override suspend fun getClubsBySport(sport: SportsClub): List<Club> {
        val clubs = courtAndGoService.clubService.getClubsBySport(sport)
        return clubs
    }

    override suspend fun getClubById(id: Int): Club {
        val clubs = courtAndGoService.clubService.getClubById(id)
        return clubs ?: throw CourtAndGoException("Club with id $id not found")
    }

    override suspend fun getClubIdByCourtId(courtId: Int): Int {
        val clubId = courtAndGoService.clubService.getClubIdByCourtId(courtId)
        return clubId
    }

    override suspend fun getClubsFiltered(
        query: String?,
        county: String?,
        district: String?,
        country: String?,
        postalCode: String?,
        sport: SportsClub
    ): List<Club> {
        val clubs = courtAndGoService.clubService.getClubsFiltered(
            query = query,
            county = county,
            district = district,
            country = country,
            postalCode = postalCode,
            sport = sport
        )
        return clubs
    }
}