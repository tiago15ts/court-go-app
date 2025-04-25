package pt.isel.courtandgo.frontend.domain

data class Owner(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val nrOfCourts: Int,
    //todo val type ?
)
