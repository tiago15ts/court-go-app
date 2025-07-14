package pt.isel.courtandgo.frontend.service.mock

import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.SportTypeCourt
import pt.isel.courtandgo.frontend.service.CourtService
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock

class MockCourtService (private val courtRepoMock: CourtRepoMock) : CourtService {

    override suspend fun getCourtsByClubId(clubId: Int): List<Court> {
        return courtRepoMock.getCourtsByClubId(clubId)
    }

    override suspend fun getCourtById(courtId: Int): Court? {
        return courtRepoMock.getCourtById(courtId)
    }

    override suspend fun getCourtsBySportType(sportType: SportTypeCourt): List<Court> {
        return courtRepoMock.getCourtsBySportType(sportType.name)
    }
}