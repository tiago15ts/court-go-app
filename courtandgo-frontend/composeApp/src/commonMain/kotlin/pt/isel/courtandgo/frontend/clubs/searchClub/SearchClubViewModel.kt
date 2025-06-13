package pt.isel.courtandgo.frontend.clubs.searchClub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.SportType
import pt.isel.courtandgo.frontend.reservations.reservationTimes.getDefaultSlotsForCourt
import pt.isel.courtandgo.frontend.service.ClubService
import pt.isel.courtandgo.frontend.service.CourtService
import pt.isel.courtandgo.frontend.service.ScheduleCourtsService
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException

sealed class ClubSearchUiState {
    object Idle : ClubSearchUiState()
    object Loading : ClubSearchUiState()
    data class Success(val clubs: List<Club>) : ClubSearchUiState()
    data class Error(val message: String) : ClubSearchUiState()
}


class SearchClubViewModel(
    private val clubService: ClubService,
    private val scheduleService : ScheduleCourtsService,
    private val courtService: CourtService
) : ViewModel() {

    private val _clubs = MutableStateFlow<List<Club>>(emptyList())
    val clubs: StateFlow<List<Club>> = _clubs.asStateFlow()

    private val _uiState = MutableStateFlow<ClubSearchUiState>(ClubSearchUiState.Idle)
    val uiState: StateFlow<ClubSearchUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow<String?>(null)
    val query: StateFlow<String?> = _query.asStateFlow()

    private val _selectedDistrict = MutableStateFlow<String?>(null)
    val selectedDistrict: StateFlow<String?> = _selectedDistrict.asStateFlow()

    private val _selectedCounty = MutableStateFlow<String?>(null)
    private val _selectedPostalCode = MutableStateFlow<String?>(null)
    private val _selectedCountry = MutableStateFlow<String?>(null)

    private val _selectedSport = MutableStateFlow(SportType.TENNIS)
    val selectedSport: StateFlow<SportType> = _selectedSport.asStateFlow()

    private val _clubHours = MutableStateFlow<Map<Int, List<LocalTime>>>(emptyMap())
    val clubHours: StateFlow<Map<Int, List<LocalTime>>> = _clubHours.asStateFlow()


    fun updateQuery(query: String?) {
        val cleaned = query?.takeIf { it.isNotBlank() }
        _query.value = cleaned
        fetchClubs()
    }

    fun updateDistrict(district: String?) {
        _selectedDistrict.value = district?.takeIf { it.isNotBlank() }
        fetchClubs()
    }

    fun updateCounty(county: String?) {
        _selectedCounty.value = county?.takeIf { it.isNotBlank() }
        fetchClubs()
    }

    fun updatePostalCode(postalCode: String?) {
        _selectedPostalCode.value = postalCode?.takeIf { it.isNotBlank() }
        fetchClubs()
    }

    fun updateCountry(country: String?) {
        _selectedCountry.value = country?.takeIf { it.isNotBlank() }
        fetchClubs()
    }

    fun updateSport(sport: SportType) {
        _selectedSport.value = sport
        fetchClubs()
    }


    fun fetchClubs() {
        viewModelScope.launch {
            _uiState.value = ClubSearchUiState.Loading
            delay(700)
            try {
                val result = clubService.getClubsFiltered(
                    query = _query.value,
                    county = _selectedCounty.value,
                    district = _selectedDistrict.value,
                    country = _selectedCountry.value,
                    postalCode = _selectedPostalCode.value,
                    sport = _selectedSport.value
                )
                _clubs.value = result
                _uiState.value = ClubSearchUiState.Success(result)

            } catch (e: CourtAndGoException) {
                _uiState.value = ClubSearchUiState.Error(e.message ?: "Erro ao pesquisar os clubes.")
            }
            catch (e: Exception) {
                _uiState.value = ClubSearchUiState.Error(e.message ?: "Erro inesperado na pesquisa de clubes.")
            }
        }
    }

    fun loadTimesForAllClubs(date: LocalDate) {
        viewModelScope.launch {
            val courtsList = _clubs.value
            val updated = courtsList.associate { club ->
                val times = getDefaultSlotsForCourt(scheduleService, club.id, date)
                club.id to times
            }
            _clubHours.value = updated
        }
    }

    suspend fun getCourtsForClub(clubId: Int): List<Court> {
        return courtService.getCourtsByClubId(clubId)
    }

    suspend fun getClubByCourtId(courtId: Int): Club? {
        val court = courtService.getCourtById(courtId)
        return court?.let { clubService.getClubById(it.clubId) }
    }


}
