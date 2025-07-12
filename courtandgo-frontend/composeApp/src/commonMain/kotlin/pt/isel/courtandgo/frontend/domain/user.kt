package pt.isel.courtandgo.frontend.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val countryCode: String,
    val phone: String,
    val gender: String? = null,
    val birthDate: String? = null,
    val weight : Double? = null,
    val height : Double? = null,
    //val location : String? = null,
)