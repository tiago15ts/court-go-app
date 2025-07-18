package pt.isel.courtandgo.frontend.service

import pt.isel.courtandgo.frontend.domain.User

interface UserService{

    suspend fun register(email: String, name: String, countryCode: String, contact: String, password: String): User

    suspend fun login(email: String, password: String): User?

    suspend fun logout(): Boolean

    suspend fun getUserById(id: Int): User?

    suspend fun getUserByEmail(email: String): User?

    suspend fun updateUser(user: User): User

    suspend fun updateUserGoogle(user: User): User

    suspend fun oauthRegister(email: String, name: String, countryCode: String, contact: String): User

    suspend fun emailNotifications(id: Int, enabled: Boolean): Boolean
}