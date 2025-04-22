package pt.isel.courtandgo.frontend.service.mock

import pt.isel.courtandgo.frontend.domain.User
import pt.isel.courtandgo.frontend.service.UserService
import pt.isel.courtandgo.frontend.service.mock.repo.UserRepoMock


class MockUserService(private val repoMock: UserRepoMock) : UserService {

    override suspend fun register(
        email: String,
        name: String,
        countryCode: String,
        contact: String,
        password: String
    ): User {
        return repoMock.createUser(email, name, countryCode, contact, password)
    }

    override suspend fun login(email: String, password: String): User? {
        return repoMock.login(email, password)
    }

    override suspend fun logout(): Boolean {
        // Mock logout logic
        return true
    }

    override suspend fun getUserById(id: Int): User? {
        return repoMock.findUserById(id)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return repoMock.findUserByEmail(email)
    }

/* todo
    override suspend fun updateUser(user: User): User {
        return repoMock.
    }

 */
}