package pt.isel.courtandgo.frontend.domain

data class Club(
    val id: Int,
    val name: String,
    val location: Location,
    val sportType: SportType,
    val nrOfCourts: Int,
    val clubOwnerId: Int,
    val averagePrice: Double,
)
