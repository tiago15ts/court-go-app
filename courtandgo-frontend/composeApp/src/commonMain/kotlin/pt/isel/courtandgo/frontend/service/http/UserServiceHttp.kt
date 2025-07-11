package pt.isel.courtandgo.frontend.service.http


import io.ktor.client.HttpClient
import pt.isel.courtandgo.frontend.domain.User
import pt.isel.courtandgo.frontend.service.UserService
import pt.isel.courtandgo.frontend.service.http.errors.AuthenticationException
import pt.isel.courtandgo.frontend.service.http.errors.NotFoundException
import pt.isel.courtandgo.frontend.service.http.errors.RegistrationException
import pt.isel.courtandgo.frontend.service.http.errors.UpdateUserException
import pt.isel.courtandgo.frontend.service.http.models.user.UpdateUserInput
import pt.isel.courtandgo.frontend.service.http.models.user.UserDTO
import pt.isel.courtandgo.frontend.service.http.models.user.UserLoginInput
import pt.isel.courtandgo.frontend.service.http.models.user.UserRegisterInput
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException
import pt.isel.courtandgo.frontend.service.http.utils.post
import pt.isel.courtandgo.frontend.service.http.utils.put

class UserServiceHttp(private val client: HttpClient) : UserService {
    override suspend fun register(
        email: String,
        name: String,
        countryCode: String,
        contact: String,
        password: String
    ): User {
        val userInput = UserRegisterInput(
            email = email,
            name = name,
            countryCode = countryCode,
            contact = contact,
            password = password
        )
        return try {
            val dto = client.post<UserDTO>("/user/register", body = userInput)
            dto.toUser()
        } catch (e: CourtAndGoException) {
            throw RegistrationException("Erro ao registar utilizador: ${e.message}", e)
        }
    }

    override suspend fun login(email: String, password: String): User? {
        val userInput = UserLoginInput(
            email = email,
            password = password
        )
        return try {
            val dto = client.post<UserDTO>("/user/login", body = userInput)
            dto.toUser()
        } catch (e: CourtAndGoException) {
            throw AuthenticationException("Erro ao autenticar utilizador: ${e.message}", e)
        }
    }

    override suspend fun logout(): Boolean {
        return try {
            client.post<Unit>("/user/logout")
            true
        } catch (e: CourtAndGoException) {
            throw AuthenticationException("Erro ao sair: ${e.message}", e)
        }
    }

    override suspend fun getUserById(id: Int): User? {
        return try {
            val dto = client.post<UserDTO>("/user/$id")
            dto.toUser()
        } catch (e: CourtAndGoException) {
            throw NotFoundException("Erro ao obter utilizador: ${e.message}", e)
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return try {
            val dto = client.post<UserDTO>("/user/email/$email")
            dto.toUser()
        } catch (e: CourtAndGoException) {
            throw NotFoundException("Erro ao obter utilizador por email: ${e.message}", e)
        }
    }

    override suspend fun updateUser(user: User): User {
        return try {
            val updateUserInput = UpdateUserInput(
                user.name,
                user.countryCode,
                user.phone,
                user.gender,
                user.birthDate,
                user.weight,
                user.height,
                user.location
            )
            val dto = client.put<UserDTO>("/user", body = updateUserInput)
            dto.toUser()
        } catch (e: CourtAndGoException) {
            throw UpdateUserException("Erro ao atualizar utilizador: ${e.message}", e)
        }
    }

    override suspend fun oauthRegister(
        email: String,
        name: String,
        countryCode: String,
        contact: String
    ): User {
        val userInput = UserRegisterInput(
            email = email,
            name = name,
            countryCode = countryCode,
            contact = contact,
            password = "" // Password is not used for OAuth registration
        )
        return try {
            val dto = client.post<UserDTO>("/user/oauth-register", body = userInput)
            dto.toUser()
        } catch (e: CourtAndGoException) {
            throw RegistrationException("Erro ao registar utilizador OAuth: ${e.message}", e)
        }
    }
}