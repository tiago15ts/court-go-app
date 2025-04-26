package pt.isel.courtandgo.frontend.service.mock

import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.service.CourtService
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock

class MockCourtService(private val courtRepoMock: CourtRepoMock) : CourtService {
    override suspend fun getAllCourts(): List<Court> {
        return courtRepoMock.getAllCourts()
    }

    override suspend fun getCourtsByDistrict(district: String): List<Court> {
        return courtRepoMock.getCourtsByDistrict(district)
    }

    override suspend fun getCourtsBySport(sport: String): List<Court> {
        return courtRepoMock.getCourtsBySport(sport)
    }

    override suspend fun getCourtsFiltered(district: String, sport: String): List<Court> {
        return courtRepoMock.getCourtsFiltered(district, sport)
    }

    override suspend fun getCourtById(id: Int): Court? {
        return courtRepoMock.getCourtById(id)
    }

    override suspend fun getCourtsByOwnerId(ownerId: Int): List<Court> {
        return courtRepoMock.getCourtsByOwnerId(ownerId)
    }

    /*
    override suspend fun getCourtsByOwnerEmail(ownerEmail: String): List<Court> {
        TODO("Not yet implemented")
    }
     */

}