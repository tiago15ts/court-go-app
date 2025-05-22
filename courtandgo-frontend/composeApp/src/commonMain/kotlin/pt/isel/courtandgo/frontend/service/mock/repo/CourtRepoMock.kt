package pt.isel.courtandgo.frontend.service.mock.repo

import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.SportType

class CourtRepoMock {

    private val courts = listOf(
        Court(1, "Beloura Tennis Academy", "Lisboa", SportType.TENNIS, "Terra Batida", 4, 1, 15.0),
        Court(2, "Lisboa Rackets", "Lisboa", SportType.PADEL, null, 4, 2, 20.0),
        Court(3, "Porto Club Padel", "Porto", SportType.PADEL, null, 6, 3, 25.0),
        Court(4, "Estoril Country Club", "Lisboa", SportType.TENNIS, "Relva", 4, 1, 30.0),
        Court(5, "Braga Tennis Club", "Braga", SportType.TENNIS, "Duro", 4, 3, 13.0),

    )

    fun getAllCourts(): List<Court> = courts

    fun getCourtsByDistrict(district: String): List<Court> {
        return courts.filter { it.district.contains(district, ignoreCase = true) }
    }

    fun getCourtsBySport(sport: SportType): List<Court> {
        return courts.filter { it.sportType == sport }
    }

    fun getCourtsFiltered(district: String, sport: SportType): List<Court> {
        return courts.filter { it.district.contains(district, ignoreCase = true)
                && it.sportType == sport }
    }

    fun getCourtById(id: Int): Court? {
        return courts.find { it.id == id }
    }
    fun getCourtsByOwnerId(ownerId: Int): List<Court> {
        return courts.filter { it.courtOwnerId == ownerId }
    }

}
