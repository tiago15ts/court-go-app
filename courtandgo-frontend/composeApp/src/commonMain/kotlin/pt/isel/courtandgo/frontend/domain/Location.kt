package pt.isel.courtandgo.frontend.domain

data class Location(
    val id: Int,
    val address: String, // only to show in details
    val county: String, //concelho
    val district: District,
    val country: Country,
    val postalCode: String,
    val latitude: Double,
    val longitude: Double,
)
