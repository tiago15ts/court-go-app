package pt.isel.courtandgo.frontend.service.http.models.user

import kotlinx.serialization.Serializable
import pt.isel.courtandgo.frontend.domain.User

@Serializable
data class LoginResponseDTO(
    val user: UserDTO,
    val accessToken: String
) {
    fun toUser() = User(user.id, user.email, user.name, user.countryCode, user.phone, user.gender,
        user.birthDate, user.weight, user.height)
}


