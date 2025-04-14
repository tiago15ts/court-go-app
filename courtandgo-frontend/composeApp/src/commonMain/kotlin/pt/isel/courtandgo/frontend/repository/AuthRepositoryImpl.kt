package pt.isel.courtandgo.frontend.repository

import pt.isel.courtandgo.frontend.domain.User
import pt.isel.courtandgo.frontend.service.CourtAndGoService

class AuthRepositoryImpl(
    private val courtAndGoService: CourtAndGoService
) : AuthRepository {
    private var token: String? = null

    override suspend fun loginWithEmail(email: String, password: String): User {
        val user = courtAndGoService.userService.login(email, password)
        return user ?: throw Exception("Login failed")
    }

    override fun setToken(token: String) {
        this.token = token
    }

    override fun getToken(): String? {
        return token
    }

    override fun logout() {
        token = null
    }

}