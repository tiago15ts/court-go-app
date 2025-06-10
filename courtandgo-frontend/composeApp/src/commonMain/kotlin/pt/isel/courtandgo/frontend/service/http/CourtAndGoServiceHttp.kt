package pt.isel.courtandgo.frontend.service.http

import io.ktor.client.HttpClient
import pt.isel.courtandgo.frontend.service.CourtAndGoService
import pt.isel.courtandgo.frontend.service.CourtService
import pt.isel.courtandgo.frontend.service.ClubService
import pt.isel.courtandgo.frontend.service.ReservationService
import pt.isel.courtandgo.frontend.service.UserService

class CourtAndGoServiceHttp(private val client: HttpClient) : CourtAndGoService {
    override val userService: UserService by lazy {
        UserServiceHttp(client)
    }

    override val clubService: ClubService by lazy {
        ClubServiceHttp(client)
    }

    override val reservationService: ReservationService by lazy {
        ReservationServiceHttp(client)
    }

    override val courtService: CourtService by lazy {
        CourtServiceHttp(client)
    }

    override val scheduleCourtsService: ScheduleCourtsServiceHttp by lazy {
        ScheduleCourtsServiceHttp(client)
    }
}