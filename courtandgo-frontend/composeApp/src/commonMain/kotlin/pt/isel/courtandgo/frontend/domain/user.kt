package pt.isel.courtandgo.frontend.domain

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val countryCode: String,
    val phone: String,
    val gender: String?,
    val birthDate: String?,
    val weight : Double?,
    val height : Double?,
    val location : String?,
)