package pt.isel.courtandgo.frontend.repository

interface AuthRepository {
    suspend fun loginWithEmail(email: String, password: String): String
    fun setToken(token: String)
    fun getToken(): String?
    fun logout()
}
