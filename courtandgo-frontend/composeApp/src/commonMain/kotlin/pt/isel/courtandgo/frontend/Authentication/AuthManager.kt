package pt.isel.courtandgo.frontend.authentication

interface AuthManager {
    fun login()
    fun logout()
    fun handleRedirect(uri: String)
    fun isLoggedIn(): Boolean
    fun getToken(): String?
    fun setToken(token: String)
}
