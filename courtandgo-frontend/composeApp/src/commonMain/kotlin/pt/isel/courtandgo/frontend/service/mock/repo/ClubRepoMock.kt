package pt.isel.courtandgo.frontend.service.mock.repo

import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Location
import pt.isel.courtandgo.frontend.domain.SportType

class ClubRepoMock {

    private val mockClubs = listOf(
        Club(1, "Beloura Tennis Academy",
            location = Location(
                id = 1,
                address = "Rua do Campo 123",
                county = "Sintra",
                district = "Lisboa",
                country = "Portugal",
                postalCode = "2710-123",
                latitude = 38.8000,
                longitude = -9.4000
            ),
            sportType = SportType.TENNIS, nrOfCourts = 4, clubOwnerId = 1, 15.0
        ),
        Club(2, "Lisboa Rackets",
            location = Location(
                id = 2,
                address = "Avenida da Liberdade 456",
                county = "Lisboa",
                district = "Lisboa",
                country = "Portugal",
                postalCode = "1250-456",
                latitude = 38.7200,
                longitude = -9.1500
            ),
            sportType = SportType.PADEL, nrOfCourts = 4, clubOwnerId = 2, 10.0
        ),
        Club(3, "Porto Club Padel",
            location = Location(
                id = 3,
                address = "Rua de Santa Catarina 789",
                county = "Porto",
                district = "Porto",
                country = "Portugal",
                postalCode = "4000-789",
                latitude = 41.1500,
                longitude = -8.6100
            ),
            sportType = SportType.PADEL, nrOfCourts = 6, clubOwnerId = 3, 12.0
        ),
        Club(4, "Estoril Country Club",
            location = Location(
                id = 4,
                address = "Avenida Marginal 101",
                county = "Cascais",
                district = "Lisboa",
                country = "Portugal",
                postalCode = "2765-101",
                latitude = 38.7000,
                longitude = -9.4000
            ),
            sportType = SportType.TENNIS, nrOfCourts = 4, clubOwnerId = 4, 20.0
        ),
        Club(5, "Braga Tennis Club",
            location = Location(
                id = 5,
                address = "Rua do Comércio 321",
                county = "Braga",
                district = "Braga",
                country = "Portugal",
                postalCode = "4700-321",
                latitude = 41.5500,
                longitude = -8.4200
            ),
            sportType = SportType.TENNIS, nrOfCourts = 4, clubOwnerId = 5, 13.5
        )
    )

    fun getAllClubs(): List<Club> = mockClubs

    fun getClubsByDistrict(district: String): List<Club> {
        return mockClubs.filter { it.location.district == district }
    }

    fun getClubsByCounty(county: String): List<Club> {
        return mockClubs.filter { it.location.county == county }
    }

    fun getClubsByCountry(country: String): List<Club> {
        return mockClubs.filter { it.location.country == country }
    }

    fun getClubsByPostalCode(postalCode: String): List<Club> {
        return mockClubs.filter { it.location.postalCode == postalCode }
    }

    fun getClubsByName(name: String): List<Club> {
        return mockClubs.filter { it.name.contains(name, ignoreCase = true) }
    }

    fun getClubsBySport(sport: SportType): List<Club> {
        return mockClubs.filter { it.sportType == sport }
    }

    /*
    fun getClubsFiltered(district: String, sport: SportType): List<Club> {
        return mockClubs.filter { it.location.district == district && it.sportType == sport }
    }
     */

    fun getClubById(id: Int): Club? {
        return mockClubs.find { it.id == id }
    }

    fun getClubsByOwnerId(ownerId: Int): List<Club> {
        return mockClubs.filter { it.clubOwnerId == ownerId }
    }

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
        sport: SportType
    ): List<Club> {
        return mockClubs.filter { club ->
            (query == null || listOf(club.name, club.location.district, club.location.county, club.location.postalCode)
                .any { it.contains(query, ignoreCase = true) }) &&
                    (district == null || club.location.district.equals(district, ignoreCase = true)) &&
                    (county == null || club.location.county.equals(county, ignoreCase = true)) &&
                    (country == null || club.location.country.equals(country, ignoreCase = true)) &&
                    (postalCode == null || club.location.postalCode.startsWith(postalCode)) &&
                    (club.sportType == sport)
        }
    }
}
