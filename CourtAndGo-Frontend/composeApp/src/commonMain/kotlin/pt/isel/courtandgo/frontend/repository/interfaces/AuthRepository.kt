package pt.isel.courtandgo.frontend.repository.interfaces

import pt.isel.courtandgo.frontend.domain.User

interface AuthRepository {
    suspend fun loginWithEmail(email: String, password: String): User
    suspend fun authenticateGoogle(tokenId: String, name: String, email: String): User

    suspend fun registerWithEmail(
        email: String,
        name: String,
        countryCode: String,
        phone: String,
        password: String
    ): User

    suspend fun updateUser(user: User): User

    fun setToken(token: String)
    fun getToken(): String?

    fun logout()
}
