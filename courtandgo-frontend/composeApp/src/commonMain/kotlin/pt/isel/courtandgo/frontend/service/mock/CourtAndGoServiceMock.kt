package pt.isel.courtandgo.frontend.service.mock

import pt.isel.courtandgo.frontend.service.CourtAndGoService
import pt.isel.courtandgo.frontend.service.UserService
import pt.isel.courtandgo.frontend.service.mock.repo.RepoMock

class CourtAndGoServiceMock : CourtAndGoService {
    private val repoMock = RepoMock()

    override val userService : UserService by lazy {
        MockUserService(repoMock.userRepoMock)
    }
}