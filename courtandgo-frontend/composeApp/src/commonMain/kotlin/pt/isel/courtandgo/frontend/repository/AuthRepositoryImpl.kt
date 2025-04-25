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

    override suspend fun authenticateGoogle(
        tokenId: String,
        name: String,
        email: String
    ): User {
        setToken(tokenId) // Guardar token

        val user = courtAndGoService.userService.getUserByEmail(email)
            ?: courtAndGoService.userService.register( //todo fix this
                email = email,
                name = name,
                countryCode = "+351",
                contact = "Atualize o seu contacto",
                password = "oauth" // ou usa null se for suportado
            )

        return user
    }

    override suspend fun registerWithEmail(
        name: String,
        email: String,
        password: String,
        countryCode: String,
        phone: String
    ): User {
        val user = courtAndGoService.userService.register(
            email = email,
            name = name,
            countryCode = countryCode,
            contact = phone,
            password = password
        )
        return user ?: throw Exception("Registration failed")
    }

    override suspend fun updateUser(user: User): User {
        return courtAndGoService.userService.updateUser(user)
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