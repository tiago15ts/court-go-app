package pt.isel.courtandgo.frontend.service.http.models.user

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginInput(
    val email: String,
    val password: String
)