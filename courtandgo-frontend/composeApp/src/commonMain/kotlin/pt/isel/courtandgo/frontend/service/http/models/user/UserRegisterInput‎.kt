package pt.isel.courtandgo.frontend.service.http.models.user

import kotlinx.serialization.Serializable


@Serializable
data class UserRegisterInput(
    val email: String,
    val name: String,
    val countryCode: String,
    val contact: String,
    //val password: String
)