package pt.isel.courtandgo.frontend

import kotlinx.datetime.LocalDateTime
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.Reservation

sealed class Screen {
    data object RegisterFirst : Screen()
    data class RegisterDetails(val email: String) : Screen()
    data object Login : Screen()
    data object Home : Screen()
    data object Profile : Screen()
    data object EditProfile : Screen()
    data object Notifications : Screen()
    //data object Calendar : Screen()

    data object LastReservations : Screen()
    data object SearchClub : Screen()
    data class SelectedClub(val club: Club, val court: Court) : Screen()
    data class ReservationDetails(
        val reservation: Reservation,
        val clubInfo: Club,
        val courtInfo: Court
    ) : Screen()
    data class ConfirmReservation(
        val clubInfo: Club,
        val courtInfo: Court,
        val playerId: Int,
        val startDateTime: LocalDateTime
    ) : Screen()
    data class ReceiptReservation(
        val reservation: Reservation,
        val clubInfo: Club,
        val courtInfo: Court,
    ) : Screen()

}

