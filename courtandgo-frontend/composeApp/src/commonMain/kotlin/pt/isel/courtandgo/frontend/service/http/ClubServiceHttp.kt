package pt.isel.courtandgo.frontend.service.http

import io.ktor.client.HttpClient
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.SportTypeCourt
import pt.isel.courtandgo.frontend.domain.SportsClub
import pt.isel.courtandgo.frontend.service.ClubService
import pt.isel.courtandgo.frontend.service.http.errors.InternalServerErrorException
import pt.isel.courtandgo.frontend.service.http.errors.NotFoundException
import pt.isel.courtandgo.frontend.service.http.models.club.ClubDTO
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException
import pt.isel.courtandgo.frontend.service.http.utils.get

class ClubServiceHttp(private val client: HttpClient) : ClubService {

    override suspend fun getAllClubs(): List<Club> {
        return try {
            val response = client.get<List<ClubDTO>>("/clubs")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw InternalServerErrorException("Failed to fetch clubs: ${e.message}", e)
        }
    }

    override suspend fun getClubsByDistrict(district: String): List<Club> {
        return try {
            val response = client.get<List<ClubDTO>>("/clubs/district/$district")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw InternalServerErrorException("Failed to fetch clubs by district: ${e.message}", e)
        }
    }

    override suspend fun getClubsByCounty(county: String): List<Club> {
        return try {
            val response = client.get<List<ClubDTO>>("/clubs/county/$county")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw InternalServerErrorException("Failed to fetch clubs by county: ${e.message}", e)
        }
    }

    override suspend fun getClubsByCountry(country: String): List<Club> {
        return try {
            val response = client.get<List<ClubDTO>>("/clubs/country/$country")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw InternalServerErrorException("Failed to fetch clubs by country: ${e.message}", e)
        }
    }

    override suspend fun getClubsByPostalCode(postalCode: String): List<Club> {
        return try {
            val response = client.get<List<ClubDTO>>("/clubs/postal/$postalCode")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw InternalServerErrorException("Failed to fetch clubs by postal code: ${e.message}", e)
        }
    }

    override suspend fun getClubsByName(name: String): List<Club> {
        return try {
            val response = client.get<List<ClubDTO>>("/clubs/name/$name")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw InternalServerErrorException("Failed to fetch clubs by name: ${e.message}", e)
        }
    }

    override suspend fun getClubsBySport(sport: SportsClub): List<Club> {
        return try {
            val response = client.get<List<ClubDTO>>("/clubs/sport/${sport.name}")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw InternalServerErrorException("Failed to fetch clubs by sport: ${e.message}", e)
        }
    }

    override suspend fun getClubById(id: Int): Club? {
        return try {
            val response = client.get<ClubDTO>("/clubs/$id")
            response.toDomain()
        } catch (e: CourtAndGoException) {
            throw NotFoundException("Club with ID $id not found: ${e.message}", e)
        }
    }

    override suspend fun getClubsByOwnerId(ownerId: Int): List<Club> {
        return try {
            val response = client.get<List<ClubDTO>>("/clubs/owner/$ownerId")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw InternalServerErrorException("Failed to fetch clubs by owner ID $ownerId: ${e.message}", e)
        }
    }

    override suspend fun getClubIdByCourtId(courtId: Int): Int {
        return try {
            client.get<Int>("/clubs/court/$courtId")
        } catch (e: CourtAndGoException) {
            throw NotFoundException("Club with court ID $courtId not found: ${e.message}", e)
        }
    }

    override suspend fun getClubsFiltered(
        query: String?,
        county: String?,
        district: String?,
        country: String?,
        postalCode: String?,
        sport: SportsClub
    ): List<Club> {
        return try {
            val parameters = mutableListOf("sport=${sport.name}")

            query?.let { parameters.add("query=$it") }
            county?.let { parameters.add("county=$it") }
            district?.let { parameters.add("district=$it") }
            country?.let { parameters.add("country=$it") }
            postalCode?.let { parameters.add("postalCode=$it") }

            val queryString = parameters.joinToString("&")
            val url = "/clubs/filter?$queryString"

            val response = client.get<List<ClubDTO>>(url)
            response.map { it.toDomain() }

        } catch (e: CourtAndGoException) {
            throw InternalServerErrorException("Failed to fetch filtered clubs: ${e.message}", e)
        }
    }

}