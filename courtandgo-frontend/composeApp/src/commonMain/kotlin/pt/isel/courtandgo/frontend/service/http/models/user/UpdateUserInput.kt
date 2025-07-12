package pt.isel.courtandgo.frontend.service.http.models.user

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserInput(
    val newName: String?,
    val newCountryCode: String?,
    val newContact: String?,
    val newGender : String?,
    val newBirthDate: String?,
    val newWeight : Double?,
    val newHeight : Double?,
    //val newLocation : String?
)
