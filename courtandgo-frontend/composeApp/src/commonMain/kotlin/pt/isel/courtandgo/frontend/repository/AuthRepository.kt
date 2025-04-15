package pt.isel.courtandgo.frontend.repository

import pt.isel.courtandgo.frontend.domain.User

interface AuthRepository {
    suspend fun loginWithEmail(email: String, password: String): User
    suspend fun loginWithGoogle(tokenId: String, name: String, email: String): User
    fun setToken(token: String)
    fun getToken(): String?
    fun logout()
}
