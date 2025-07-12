package pt.isel.courtandgo.frontend.service.http.models.user

import kotlinx.serialization.Serializable
import pt.isel.courtandgo.frontend.domain.User

@Serializable
data class UserDTO(
    val id: Int,
    val email: String,
    val name: String,
    val countryCode: String,
    val phone: String,
    val gender: String?,
    val birthDate: String?,
    val weight : Double?,
    val height : Double?,

) {
    fun toUser() = User(id, email, name, countryCode, phone, gender, birthDate, weight, height)
}

