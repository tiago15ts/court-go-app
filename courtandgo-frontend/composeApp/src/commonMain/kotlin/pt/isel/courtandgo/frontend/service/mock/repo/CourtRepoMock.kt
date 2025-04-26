package pt.isel.courtandgo.frontend.service.mock.repo

import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.service.CourtService

class CourtRepoMock {

    private val courts = listOf(
        Court(1, "Beloura Tennis Academy", "Lisboa", "Ténis", "Terra Batida",4, 1, 15.0,
            listOf("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00")),
        Court(2, "Lisboa Rackets", "Lisboa", "Padel", null,4, 2, 20.0,
            listOf("12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00")),
        Court(3, "Porto Club Padel", "Porto", "Padel", null,6, 3, 25.0,
            listOf("10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00") ),
        Court(4, "Estoril Country Club", "Lisboa", "Ténis", "Relva",4, 1, 30.0,
            listOf("09:00", "10:00", "11:00", "12:00") ),
        Court(5, "Braga Tennis Club", "Braga", "Ténis", "Duro",4, 3, 13.0,
            listOf("10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00")),

    )

    fun getAllCourts(): List<Court> = courts

    fun getCourtsByDistrict(district: String): List<Court> {
        return courts.filter { it.district.contains(district, ignoreCase = true) }
    }

    fun getCourtsBySport(sport: String): List<Court> {
        return courts.filter { it.sportType.equals(sport, ignoreCase = true) }
    }

    fun getCourtsFiltered(district: String, sport: String): List<Court> {
        return courts.filter {
            it.district.contains(district, ignoreCase = true) &&
                    it.sportType.equals(sport, ignoreCase = true)
        }
    }

    fun getCourtById(id: Int): Court? {
        return courts.find { it.id == id }
    }
    fun getCourtsByOwnerId(ownerId: Int): List<Court> {
        return courts.filter { it.courtOwnerId == ownerId }
    }

}
