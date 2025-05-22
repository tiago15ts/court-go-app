package pt.isel.courtandgo.frontend.courts.searchCourt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.SportType
import pt.isel.courtandgo.frontend.reservations.reservationTimes.getTimeSlotsForCourt
import pt.isel.courtandgo.frontend.service.CourtService
import pt.isel.courtandgo.frontend.service.ScheduleCourtsService

class CourtSearchViewModel(
    private val courtService: CourtService,
    private val scheduleService : ScheduleCourtsService
) : ViewModel() {

    private val _courts = MutableStateFlow<List<Court>>(emptyList())
    val courts: StateFlow<List<Court>> = _courts.asStateFlow()

    private val _selectedDistrict = MutableStateFlow("")
    val selectedDistrict: StateFlow<String> = _selectedDistrict.asStateFlow()

    private val _selectedSport = MutableStateFlow(SportType.TENNIS)
    val selectedSport: StateFlow<SportType> = _selectedSport.asStateFlow()

    private val _courtHours = MutableStateFlow<Map<Int, List<LocalTime>>>(emptyMap())
    val courtHours: StateFlow<Map<Int, List<LocalTime>>> = _courtHours.asStateFlow()


    fun updateDistrict(district: String) {
        _selectedDistrict.value = district
        fetchCourts()
    }

    fun updateSport(sport: SportType) {
        _selectedSport.value = sport
        fetchCourts()
    }

     fun fetchCourts() {
        val district = _selectedDistrict.value
        val sport = _selectedSport.value

        viewModelScope.launch {
            val result = courtService.getCourtsFiltered(district, sport)
            _courts.value = result

        }
    }

    fun loadTimesForAllCourts(date: LocalDate) {
        viewModelScope.launch {
            val courtsList = _courts.value
            val updated = courtsList.associate { court ->
                val times = getTimeSlotsForCourt(scheduleService, court.id, date)
                court.id to times
            }
            _courtHours.value = updated
        }
    }
}
