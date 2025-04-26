package pt.isel.courtandgo.frontend.reservations.searchCourt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.service.CourtService

class CourtSearchViewModel(
    private val courtService: CourtService
) : ViewModel() {

    private val _courts = MutableStateFlow<List<Court>>(emptyList())
    val courts: StateFlow<List<Court>> = _courts.asStateFlow()

    private val _selectedDistrict = MutableStateFlow("")
    val selectedDistrict: StateFlow<String> = _selectedDistrict.asStateFlow()

    private val _selectedSport = MutableStateFlow("Ténis")
    val selectedSport: StateFlow<String> = _selectedSport.asStateFlow()

    fun updateDistrict(district: String) {
        _selectedDistrict.value = district
        fetchCourts()
    }

    fun updateSport(sport: String) {
        if (sport != _selectedSport.value) {
            _selectedSport.value = sport
            fetchCourts()
        }
    }

     fun fetchCourts() {
        val district = _selectedDistrict.value
        val sport = _selectedSport.value

        viewModelScope.launch {
            val result = courtService.getCourtsFiltered(district, sport)
            _courts.value = result
        }
    }
}
