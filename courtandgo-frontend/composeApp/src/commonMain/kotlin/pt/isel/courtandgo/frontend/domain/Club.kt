package pt.isel.courtandgo.frontend.domain

import kotlinx.serialization.Serializable

data class Club(
    val id: Int,
    val name: String,
    val location: Location,
    val sportsClub: SportsClub,
    val nrOfCourts: Int,
    val averagePrice: Double,
)

@Serializable
enum class SportsClub {
    Tennis, Padel, Both
}