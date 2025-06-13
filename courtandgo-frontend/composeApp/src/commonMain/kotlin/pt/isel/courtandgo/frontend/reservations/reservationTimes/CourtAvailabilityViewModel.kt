package pt.isel.courtandgo.frontend.reservations.reservationTimes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

import pt.isel.courtandgo.frontend.service.CourtService
import pt.isel.courtandgo.frontend.service.ReservationService
import pt.isel.courtandgo.frontend.service.ScheduleCourtsService
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException

sealed class CourtAvailabilityUiState {
    object Loading : CourtAvailabilityUiState()
    data class Success(
        val availableSlots: Map<Int, List<LocalTime>>,
        val defaultTimes: List<LocalTime>
    ) : CourtAvailabilityUiState()
    data class Error(val message: String) : CourtAvailabilityUiState()
}

class CourtAvailabilityViewModel(
    private val scheduleService: ScheduleCourtsService,
    private val reservationService: ReservationService,
    private val courtService: CourtService
) : ViewModel() {

    private val _uiState = mutableStateOf<CourtAvailabilityUiState>(CourtAvailabilityUiState.Loading)
    val uiState: State<CourtAvailabilityUiState> = _uiState

    private val _availableSlots = mutableStateOf<Map<Int, List<LocalTime>>>(emptyMap())
    val availableSlots: State<Map<Int, List<LocalTime>>> = _availableSlots

    private val _defaultTimes = mutableStateOf<List<LocalTime>>(emptyList())
    private val defaultTimes: State<List<LocalTime>> = _defaultTimes


    fun loadAvailableSlots(clubId: Int, date: LocalDate) {
        viewModelScope.launch {
            _uiState.value = CourtAvailabilityUiState.Loading
            try {
                val courts = courtService.getCourtsByClubId(clubId)
                val courtIds = courts.map { it.id }

                val timeSlotsByCourt = courtIds.associateWith {
                    getDefaultSlotsForCourt(scheduleService, it, date)
                }

                _defaultTimes.value = timeSlotsByCourt.values.flatten().distinct()

                val reservations = reservationService.getReservationsByCourtIdsAndDate(courtIds, date)
                val occupiedTimesByCourt = reservations.groupBy { it.courtId }.mapValues { entry ->
                    entry.value.map { it.startTime.time }
                }

                val available = getAvailableTimeSlotsForClub(timeSlotsByCourt, occupiedTimesByCourt)
                _availableSlots.value = available

                _uiState.value = CourtAvailabilityUiState.Success(
                    availableSlots = available,
                    defaultTimes = defaultTimes.value
                )
            } catch (e: CourtAndGoException) {
                _uiState.value = CourtAvailabilityUiState.Error(e.message ?: "Erro ao carregar os dados.")
            } catch (e: Exception) {
                _uiState.value = CourtAvailabilityUiState.Error("Erro inesperado ao carregar os dados.")
            }
        }
    }
}
