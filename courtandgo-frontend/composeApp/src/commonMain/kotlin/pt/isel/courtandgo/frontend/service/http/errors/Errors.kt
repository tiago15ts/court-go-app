package pt.isel.courtandgo.frontend.service.http.errors


class RegistrationException(message: String, cause: Throwable? = null) : Exception(message, cause)

class AuthenticationException(message: String, cause: Throwable? = null) : Exception(message, cause)

class UpdateUserException(message: String, cause: Throwable? = null) : Exception(message, cause)

class NetworkException(message: String, cause: Throwable? = null) : Exception(message, cause)

class NotFoundException(message: String, cause: Throwable? = null) : Exception(message, cause)

class BadRequestException(message: String, cause: Throwable? = null) : Exception(message, cause)

class UpdateReservationException(message: String, cause: Throwable? = null) : Exception(message, cause)

class InternalServerErrorException(message: String, cause: Throwable? = null) : Exception(message, cause)



