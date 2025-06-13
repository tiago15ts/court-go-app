package pt.isel.courtandgo.frontend.service.http.errors

import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException


class RegistrationException(message: String, cause: Throwable? = null) : CourtAndGoException(message, cause)

class AuthenticationException(message: String, cause: Throwable? = null) : CourtAndGoException(message, cause)

class UpdateUserException(message: String, cause: Throwable? = null) : CourtAndGoException(message, cause)

class NetworkException(message: String, cause: Throwable? = null) : CourtAndGoException(message, cause)

class NotFoundException(message: String, cause: Throwable? = null) : CourtAndGoException(message, cause)

class BadRequestException(message: String, cause: Throwable? = null) : CourtAndGoException(message, cause)

class UpdateReservationException(message: String, cause: Throwable? = null) : CourtAndGoException(message, cause)

class InternalServerErrorException(message: String, cause: Throwable? = null) : CourtAndGoException(message, cause)



