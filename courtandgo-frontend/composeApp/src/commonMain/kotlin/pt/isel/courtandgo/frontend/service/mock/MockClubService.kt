package pt.isel.courtandgo.frontend.service.mock

import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.SportType
import pt.isel.courtandgo.frontend.service.ClubService
import pt.isel.courtandgo.frontend.service.mock.repo.ClubRepoMock

class MockClubService(private val clubRepoMock: ClubRepoMock) : ClubService {
    override suspend fun getAllClubs(): List<Club> {
        return clubRepoMock.getAllClubs()
    }

    override suspend fun getClubsByDistrict(district: String): List<Club> {
        return clubRepoMock.getClubsByDistrict(district)
    }

    override suspend fun getClubsByCounty(county: String): List<Club> {
        return clubRepoMock.getClubsByCounty(county)
    }

    override suspend fun getClubsByCountry(country: String): List<Club> {
        return clubRepoMock.getClubsByCountry(country)
    }

    override suspend fun getClubsByPostalCode(postalCode: String): List<Club> {
        return clubRepoMock.getClubsByPostalCode(postalCode)
    }

    override suspend fun getClubsByName(name: String): List<Club> {
        return clubRepoMock.getClubsByName(name)
    }

    override suspend fun getClubsBySport(sport: SportType): List<Club> {
        return clubRepoMock.getClubsBySport(sport)
    }

    /*
    override suspend fun getClubsFiltered(district: String, sport: SportType): List<Club> {
        return clubRepoMock.getClubsFiltered(district, sport)
    }

     */

    override suspend fun getClubById(id: Int): Club? {
        return clubRepoMock.getClubById(id)
    }

    override suspend fun getClubsByOwnerId(ownerId: Int): List<Club> {
        return clubRepoMock.getClubsByOwnerId(ownerId)
    }

    override suspend fun getClubsFiltered(
        query: String?,
        county: String?,
        district: String?,
        country: String?,
        postalCode: String?,
        sport: SportType
    ): List<Club> {
        return clubRepoMock.getClubsFiltered(query, county, district, country, postalCode, sport)
    }

}