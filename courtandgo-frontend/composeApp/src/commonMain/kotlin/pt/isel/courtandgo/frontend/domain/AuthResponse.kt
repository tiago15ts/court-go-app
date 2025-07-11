package pt.isel.courtandgo.frontend.domain

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val accessToken: String,
    val idToken: String,
    val refreshToken: String,
    val user: User
)
