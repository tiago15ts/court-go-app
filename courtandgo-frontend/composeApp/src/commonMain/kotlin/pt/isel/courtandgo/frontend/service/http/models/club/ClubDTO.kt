package pt.isel.courtandgo.frontend.service.http.models.club


import kotlinx.serialization.Serializable
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.SportType
import pt.isel.courtandgo.frontend.service.http.models.location.LocationDTO

@Serializable
data class ClubDTO(
    val id: Int,
    val name: String,
    val location: LocationDTO,
    val sportType: SportType,
    val nrOfCourts: Int,
    val clubOwnerId: Int,
    val averagePrice: Double
) {
    fun toDomain(): Club = Club(
        id = id,
        name = name,
        location = location.toDomain(),
        sportType = sportType,
        nrOfCourts = nrOfCourts,
        clubOwnerId = clubOwnerId,
        averagePrice = averagePrice
    )

    companion object {
        fun fromDomain(club: Club) = ClubDTO(
            id = club.id,
            name = club.name,
            location = LocationDTO.fromDomain(club.location),
            sportType = club.sportType,
            nrOfCourts = club.nrOfCourts,
            clubOwnerId = club.clubOwnerId,
            averagePrice = club.averagePrice
        )
    }
}
