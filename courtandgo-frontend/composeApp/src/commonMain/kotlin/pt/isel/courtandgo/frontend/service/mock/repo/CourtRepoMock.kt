package pt.isel.courtandgo.frontend.service.mock.repo

import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.SportTypeCourt

class CourtRepoMock {

    private val courts = mutableListOf<Court>(
        Court(
            id = 1,
            name = "Court 1",
            clubId = 1,
            sportTypeCourt = SportTypeCourt.Tennis,
            surfaceType = "Terra Batida",
            capacity = 4,
            price = 15.0,
        ),
        Court(
            id = 2,
            name = "Court 2",
            clubId = 4,
            sportTypeCourt = SportTypeCourt.Tennis,
            surfaceType = "Relva",
            capacity = 6,
            price = 20.0,
        ),
        Court(
            id = 3,
            name = "Court 3",
            clubId = 2,
            sportTypeCourt = SportTypeCourt.Padel,
            surfaceType = null,
            capacity = 4,
            price = 10.0,
        ),
        Court(
            id = 4,
            name = "Court 2",
            clubId = 1,
            sportTypeCourt = SportTypeCourt.Tennis,
            surfaceType = "Terra Batida",
            capacity = 4,
            price = 15.0,
        ),

    )

    fun getCourtsByClubId(clubId: Int): List<Court> {
        return courts.filter { it.clubId == clubId }
    }

    fun getCourtById(courtId: Int): Court? {
        return courts.find { it.id == courtId }
    }

    fun getCourtsBySportType(sportType: String): List<Court> {
        return courts.filter { it.sportTypeCourt.toString() == sportType }
    }
}