package pt.isel.courtandgo.frontend.service.http


import io.ktor.client.HttpClient
import pt.isel.courtandgo.frontend.authentication.TokenHolder
import pt.isel.courtandgo.frontend.domain.User
import pt.isel.courtandgo.frontend.service.UserService
import pt.isel.courtandgo.frontend.service.http.errors.AuthenticationException
import pt.isel.courtandgo.frontend.service.http.errors.NotFoundException
import pt.isel.courtandgo.frontend.service.http.errors.RegistrationException
import pt.isel.courtandgo.frontend.service.http.errors.UpdateUserException
import pt.isel.courtandgo.frontend.service.http.models.user.LoginResponseDTO
import pt.isel.courtandgo.frontend.service.http.models.user.RegisterResponseDto
import pt.isel.courtandgo.frontend.service.http.models.user.UpdateUserInput
import pt.isel.courtandgo.frontend.service.http.models.user.UserDTO
import pt.isel.courtandgo.frontend.service.http.models.user.UserLoginInput
import pt.isel.courtandgo.frontend.service.http.models.user.UserRegisterInput
import pt.isel.courtandgo.frontend.service.http.models.user.emailNotificationInput
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
            val dto = client.post<RegisterResponseDto>("/user/register", body = userInput)
            TokenHolder.accessToken = dto.accessToken
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
            val dto = client.post<LoginResponseDTO>("/user/login", body = userInput)
            TokenHolder.accessToken = dto.accessToken
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
            return null // If user not found, return null on purpose, to google authentication
        }
    }

    override suspend fun updateUser(user: User): User {
        return try {
            val updateUserInput = UpdateUserInput(
                user.email,
                user.name,
                user.countryCode,
                user.phone,
                user.gender,
                user.birthDate,
                user.weight,
                user.height,
                //user.location
            )
            val token = TokenHolder.accessToken
            val dto = client.put<UserDTO>("/user/${user.id}", body = updateUserInput, token = token)
            dto.toUser()
        } catch (e: CourtAndGoException) {
            throw UpdateUserException("Erro ao atualizar utilizador: ${e.message}", e)
        }
    }

    override suspend fun updateUserGoogle(user: User): User {
        return try {
            val updateUserInput = UpdateUserInput(
                user.email,
                user.name,
                user.countryCode,
                user.phone,
                user.gender,
                user.birthDate,
                user.weight,
                user.height,
                //user.location
            )
            val res = client.put<UserDTO>("/user/google/${user.id}", body = updateUserInput)
            res.toUser()
        } catch (e: CourtAndGoException) {
            throw UpdateUserException("Erro ao atualizar utilizador do google: ${e.message}", e)
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
            val dto = client.post<UserDTO>("/user/oauthregister", body = userInput)
            dto.toUser()
        } catch (e: CourtAndGoException) {
            throw RegistrationException("Erro ao registar utilizador OAuth: ${e.message}", e)
        }
    }

    override suspend fun emailNotifications(id: Int, enabled: Boolean): Boolean {
        return try {
            val input = emailNotificationInput(
                enabled = enabled
            )
            val res = client.put<Boolean>("/user/emailnotification/${id}", body = input)
            res
        } catch (e: CourtAndGoException) {
            throw UpdateUserException("Erro ao atualizar notificações por email: ${e.message}", e)
        }
    }
}