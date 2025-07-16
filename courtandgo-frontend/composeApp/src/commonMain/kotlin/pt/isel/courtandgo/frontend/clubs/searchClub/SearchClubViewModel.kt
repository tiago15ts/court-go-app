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
import pt.isel.courtandgo.frontend.domain.SportsClub
import pt.isel.courtandgo.frontend.repository.interfaces.ClubRepository
import pt.isel.courtandgo.frontend.repository.interfaces.CourtRepository
import pt.isel.courtandgo.frontend.repository.interfaces.ScheduleRepository
import pt.isel.courtandgo.frontend.reservations.reservationTimes.getDefaultSlotsForCourt
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException
import pt.isel.courtandgo.frontend.utils.dateUtils.currentDate

sealed class ClubSearchUiState {
    object Idle : ClubSearchUiState()
    object Loading : ClubSearchUiState()
    data class Success(val clubs: List<Club>) : ClubSearchUiState()
    data class Error(val message: String) : ClubSearchUiState()
}


class SearchClubViewModel(
    private val clubRepository: ClubRepository,
    private val scheduleRepo: ScheduleRepository,
    private val courtRepository: CourtRepository,
) : ViewModel() {

    private val _clubs = MutableStateFlow<List<Club>>(emptyList())
    val clubs: StateFlow<List<Club>> = _clubs.asStateFlow()

    val _uiState = MutableStateFlow<ClubSearchUiState>(ClubSearchUiState.Idle)
    val uiState: StateFlow<ClubSearchUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow<String?>(null)
    val query: StateFlow<String?> = _query.asStateFlow()

    private val _selectedDistrict = MutableStateFlow<String?>(null)
    val selectedDistrict: StateFlow<String?> = _selectedDistrict.asStateFlow()

    private val _selectedCounty = MutableStateFlow<String?>(null)
    val selectedCounty: StateFlow<String?> = _selectedCounty.asStateFlow()
    private val _selectedPostalCode = MutableStateFlow<String?>(null)
    val selectedPostalCode: StateFlow<String?> = _selectedPostalCode.asStateFlow()
    private val _selectedCountry = MutableStateFlow<String?>(null)
    val selectedCountry: StateFlow<String?> = _selectedCountry.asStateFlow()

    private val _selectedSport = MutableStateFlow(SportsClub.Tennis)
    val selectedSport: StateFlow<SportsClub> = _selectedSport.asStateFlow()

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

    fun updateSport(sport: SportsClub) {
        _selectedSport.value = sport
        fetchClubs()
    }


    fun fetchClubs() {
        viewModelScope.launch {
            _uiState.value = ClubSearchUiState.Loading
            try {
                val result = clubRepository.getClubsFiltered(
                    query = _query.value,
                    county = _selectedCounty.value,
                    district = _selectedDistrict.value,
                    country = _selectedCountry.value,
                    postalCode = _selectedPostalCode.value,
                    sport = _selectedSport.value
                )
                _clubs.value = result

                loadTimesForAllClubs(currentDate)
                _uiState.value = ClubSearchUiState.Success(result)

            } catch (e: CourtAndGoException) {
                _uiState.value = ClubSearchUiState.Error(e.message ?: "Erro ao pesquisar os clubes.")
            }
            catch (e: Exception) {
                _uiState.value = ClubSearchUiState.Error(e.message ?: "Erro inesperado na pesquisa de clubes.")
            }
        }
    }

    private fun loadTimesForAllClubs(date: LocalDate) {
        viewModelScope.launch {
            try {
                val clubs = _clubs.value
                val result = mutableMapOf<Int, List<LocalTime>>()

                for (club in clubs) {
                    try {

                        val courts = courtRepository.getCourtsByClubId(club.id)
                        val times = courts.flatMap { court ->
                            try {
                                getDefaultSlotsForCourt(scheduleRepo, court.id, date)
                            } catch (e: CourtAndGoException) {
                                println("Erro ao carregar horários para o court ${court.id} do (clube ${club.name}): ${e.message}")
                                emptyList()
                            }
                        }
                        result[club.id] = times.distinct()
                    } catch (e: Exception) {
                        println("Erro ao obter courts para o clube ${club.name}: ${e.message}")
                        result[club.id] = emptyList()
                    }
                }
                _clubHours.value = result

            } catch (e: Exception) {
                _clubHours.value = emptyMap()
                println("Erro inesperado ao carregar horários de todos os clubes: ${e.message}")
            }
        }
    }


    suspend fun getCourtsForClub(clubId: Int): List<Court> {
        return courtRepository.getCourtsByClubId(clubId)
    }

    suspend fun getClubByCourtId(courtId: Int): Club? {
        val court = courtRepository.getCourtById(courtId)
        return court?.let { clubRepository.getClubById(it.clubId) }
    }


}
