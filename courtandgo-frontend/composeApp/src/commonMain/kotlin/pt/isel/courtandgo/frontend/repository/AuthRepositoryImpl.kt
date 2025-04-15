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

    override suspend fun loginWithGoogle(
        tokenId: String,
        name: String,
        email: String
    ): User {
        setToken(tokenId) // Guardar token

        val user = courtAndGoService.userService.getUserByEmail(email)
            ?: courtAndGoService.userService.register(
                email = email,
                name = name,
                contact = "Atualize o seu contacto",
                password = "oauth" // ou usa null se for suportado
            )

        return user
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