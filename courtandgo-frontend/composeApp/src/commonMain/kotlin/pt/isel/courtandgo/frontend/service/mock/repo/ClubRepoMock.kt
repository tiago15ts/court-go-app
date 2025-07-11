package pt.isel.courtandgo.frontend.service.mock.repo

import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Country
import pt.isel.courtandgo.frontend.domain.District
import pt.isel.courtandgo.frontend.domain.Location
import pt.isel.courtandgo.frontend.domain.SportsClub

class ClubRepoMock {
    private val countryPortugal = Country(1, "Portugal")
    private val districtLisboa = District(1,"Lisboa", 1)
    private val districtPorto = District(2, "Porto", 1)
    private val districtBraga = District(3, "Braga", 1)

    private val mockClubs = listOf(

        Club(
            1, "Beloura Tennis Academy",
            location = Location(
                id = 1,
                address = "Rua do Campo 123",
                county = "Sintra",
                district = districtLisboa,
                country = countryPortugal,
                postalCode = "2710-123",
                latitude = 38.8000,
                longitude = -9.4000
            ),
            sportsClub = SportsClub.Tennis, nrOfCourts = 4, 15.0
        ),
        Club(
            2, "Lisboa Rackets",
            location = Location(
                id = 2,
                address = "Avenida da Liberdade 456",
                county = "Lisboa",
                district = districtLisboa,
                country = countryPortugal,
                postalCode = "1250-456",
                latitude = 38.7200,
                longitude = -9.1500
            ),
            sportsClub = SportsClub.Padel, nrOfCourts = 4, 10.0
        ),
        Club(
            3, "Porto Club Padel",
            location = Location(
                id = 3,
                address = "Rua de Santa Catarina 789",
                county = "Porto",
                district = districtPorto,
                country = countryPortugal,
                postalCode = "4000-789",
                latitude = 41.1500,
                longitude = -8.6100
            ),
            sportsClub = SportsClub.Padel, nrOfCourts = 6, 12.0
        ),
        Club(
            4, "Estoril Country Club",
            location = Location(
                id = 4,
                address = "Avenida Marginal 101",
                county = "Cascais",
                district = districtLisboa,
                country = countryPortugal,
                postalCode = "2765-101",
                latitude = 38.7000,
                longitude = -9.4000
            ),
            sportsClub = SportsClub.Tennis, nrOfCourts = 4, 20.0
        ),
        Club(
            5, "Braga Tennis Club",
            location = Location(
                id = 5,
                address = "Rua do Comércio 321",
                county = "Braga",
                district = districtBraga,
                country = countryPortugal,
                postalCode = "4700-321",
                latitude = 41.5500,
                longitude = -8.4200
            ),
            sportsClub = SportsClub.Tennis, nrOfCourts = 4, 13.5
        )
    )

    fun getAllClubs(): List<Club> = mockClubs

    fun getClubsByDistrict(district: String): List<Club> {
        return mockClubs.filter { it.location.district.name == district }
    }

    fun getClubsByCounty(county: String): List<Club> {
        return mockClubs.filter { it.location.county == county }
    }

    fun getClubsByCountry(country: String): List<Club> {
        return mockClubs.filter { it.location.country.name == country }
    }

    fun getClubsByPostalCode(postalCode: String): List<Club> {
        return mockClubs.filter { it.location.postalCode == postalCode }
    }

    fun getClubsByName(name: String): List<Club> {
        return mockClubs.filter { it.name.contains(name, ignoreCase = true) }
    }

    fun getClubsBySport(sport: SportsClub): List<Club> {
        return mockClubs.filter { it.sportsClub == sport }
    }

    /*
    fun getClubsFiltered(district: String, sport: SportType): List<Club> {
        return mockClubs.filter { it.location.district == district && it.sportType == sport }
    }
     */

    fun getClubById(id: Int): Club? {
        return mockClubs.find { it.id == id }
    }

    /*
    fun getClubsByOwnerId(ownerId: Int): List<Club> {
        return mockClubs.filter { it.clubOwnerId == ownerId }
    }

     */

    fun getClubIdByCourtId(courtId: Int): Int {
        // This is a mock implementation, assuming each club has a unique court ID
        return mockClubs.firstOrNull { it.id == courtId }
            ?.id ?: throw NoSuchElementException("No club found with court ID $courtId")
    }

    fun getClubsFiltered(
        query: String? = null,
        county: String? = null,
        district: String? = null,
        country: String? = null,
        postalCode: String? = null,
        sport: SportsClub
    ): List<Club> {
        val clubs = mockClubs.filter { club ->
            (query == null || listOf(
                club.name,
                club.location.district.name,
                club.location.county,
                club.location.postalCode,
                club.location.country.name
            ).any { it.contains(query, ignoreCase = true) }) &&
                    (district == null || club.location.district.name.equals(district, ignoreCase = true)) &&
                    (county == null || club.location.county.equals(county, ignoreCase = true)) &&
                    (country == null || club.location.country.name.equals(country, ignoreCase = true)) &&
                    (postalCode == null || club.location.postalCode.startsWith(postalCode)) &&
                    (club.sportsClub == sport)
        }
        return clubs
    }
}
