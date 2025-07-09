package pt.isel.courtandgo.frontend.service.http.models.club


import kotlinx.serialization.Serializable
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.SportsClub
import pt.isel.courtandgo.frontend.service.http.models.location.LocationDTO

@Serializable
data class ClubDTO(
    val id: Int,
    val name: String,
    val location: LocationDTO,
    val sportsClub: SportsClub,
    val nrOfCourts: Int,
    val clubOwnerId: Int,
    val averagePrice: Double
) {
    fun toDomain(): Club = Club(
        id = id,
        name = name,
        location = location.toDomain(),
        sportsClub = sportsClub,
        nrOfCourts = nrOfCourts,
        clubOwnerId = clubOwnerId,
        averagePrice = averagePrice
    )

    companion object {
        fun fromDomain(club: Club) = ClubDTO(
            id = club.id,
            name = club.name,
            location = LocationDTO.fromDomain(club.location),
            sportsClub = club.sportsClub,
            nrOfCourts = club.nrOfCourts,
            clubOwnerId = club.clubOwnerId,
            averagePrice = club.averagePrice
        )
    }
}
