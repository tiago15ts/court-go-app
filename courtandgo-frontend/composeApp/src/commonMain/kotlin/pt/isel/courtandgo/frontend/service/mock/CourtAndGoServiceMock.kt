package pt.isel.courtandgo.frontend.service.mock

import pt.isel.courtandgo.frontend.service.CourtAndGoService
import pt.isel.courtandgo.frontend.service.ClubService
import pt.isel.courtandgo.frontend.service.CourtService
import pt.isel.courtandgo.frontend.service.ReservationService
import pt.isel.courtandgo.frontend.service.UserService
import pt.isel.courtandgo.frontend.service.mock.repo.RepoMock

class CourtAndGoServiceMock() : CourtAndGoService {
    private val repoMock = RepoMock()

    override val userService : UserService by lazy {
        MockUserService(repoMock.userRepoMock)
    }

    override val clubService : ClubService by lazy {
        MockClubService(repoMock.clubRepoMock)
    }

    override val reservationService : ReservationService by lazy {
        MockReservationService(repoMock.reservationRepoMock)
    }

    override val courtService : CourtService by lazy {
        MockCourtService(repoMock.courtRepoMock)
    }

    override val scheduleCourtsService by lazy {
        MockScheduleCourtService(repoMock.scheduleCourtRepoMock)
    }
}