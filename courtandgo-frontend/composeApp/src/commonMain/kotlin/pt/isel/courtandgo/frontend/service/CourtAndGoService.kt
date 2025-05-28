package pt.isel.courtandgo.frontend.service

interface CourtAndGoService {
    val userService: UserService
    val clubService: ClubService
    val reservationService: ReservationService
    val courtService: CourtService
}