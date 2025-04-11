package pt.isel.courtandgo.frontend

sealed class Screen {
    data object RegisterFirst : Screen()
    data class RegisterDetails(val email: String) : Screen()
    data object Login : Screen()
    data object Home : Screen()
}

