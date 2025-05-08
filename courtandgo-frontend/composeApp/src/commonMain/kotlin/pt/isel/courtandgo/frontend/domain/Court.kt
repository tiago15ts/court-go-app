package pt.isel.courtandgo.frontend.domain

data class Court(
    val id: Int,
    val name: String,
    val district: String,
    //todo val location: Location,
    val sportType: String,
    val surfaceType: String?,
    val capacity: Int,
    val courtOwnerId: Int,
    val price: Double,
)
