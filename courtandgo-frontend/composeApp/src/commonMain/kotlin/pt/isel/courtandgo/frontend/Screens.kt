package pt.isel.courtandgo.frontend

import pt.isel.courtandgo.frontend.domain.Court

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
    data class Reservation(val court: Court) : Screen()

}

