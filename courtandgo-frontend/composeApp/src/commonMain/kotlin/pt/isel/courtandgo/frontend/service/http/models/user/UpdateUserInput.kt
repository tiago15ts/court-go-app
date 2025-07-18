package pt.isel.courtandgo.frontend.service.http.models.user

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserInput(
    val email: String?,
    val name: String?,
    val countryCode: String?,
    val phone: String?,
    val gender : String?,
    val birthdate: String?,
    val weight : Double?,
    val height : Double?,
    //val newLocation : String?
)
