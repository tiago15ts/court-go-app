package pt.isel.courtandgo.frontend.service

interface CourtAndGoService {
    val userService: UserService
    val courtService: CourtService
    val reservationService: ReservationService
}