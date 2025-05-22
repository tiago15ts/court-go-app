package pt.isel.courtandgo.frontend

import kotlinx.datetime.LocalDateTime
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.Reservation

sealed class Screen {
    data object RegisterFirst : Screen()
    data class RegisterDetails(val email: String) : Screen()
    data object Login : Screen()
    data object Home : Screen()
    data object SearchCourt : Screen()
    data object LastReservations : Screen()
    //data object Calendar : Screen()
    data object Profile : Screen()
    data object EditProfile : Screen()
    data object Notifications : Screen()
    data class ReserveCourt(val court: Court) : Screen()
    data class ReservationDetails(val reservation: Reservation) : Screen()
    data class ConfirmReservation(
        val courtId: Int,
        val courtName: String,
        val playerId: Int,
        val startDateTime: LocalDateTime,
        val pricePerHour: Double
    ) : Screen()
    data class ReceiptReservation(
        val reservation: Reservation
    ) : Screen()

}

