package pt.isel.courtandgo.frontend.reservations.reservationTimes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import pt.isel.courtandgo.frontend.service.CourtService
import pt.isel.courtandgo.frontend.service.ReservationService
import pt.isel.courtandgo.frontend.service.ScheduleCourtsService

class CourtAvailabilityViewModel(
    private val scheduleService: ScheduleCourtsService,
    private val reservationService: ReservationService,
    private val courtService: CourtService
) : ViewModel() {

    private val _availableSlots = mutableStateOf<List<LocalTime>>(emptyList())
    val availableSlots: State<List<LocalTime>> = _availableSlots

    fun loadAvailableSlots(clubId: Int, date: LocalDate) {
        viewModelScope.launch {
            val courts = courtService.getCourtsByClubId(clubId)
            val courtIds = courts.map { it.id }

            val timeSlotsByCourt = courtIds.associateWith {
                getDefaultSlotsForCourt(scheduleService, it, date)
            }

            val allReservations = reservationService.getReservations()
            val occupiedTimesByCourt = reservationService.getReservationsForClubOnDate(allReservations, courtIds, date)

            val available = reservationService.getAvailableTimeSlotsForClub(timeSlotsByCourt, occupiedTimesByCourt)
            _availableSlots.value = available
        }
    }

    fun loadTimesForDate(courtId: Int, date: LocalDate) {
        viewModelScope.launch {
            val times = getDefaultSlotsForCourt(scheduleService, courtId, date)
            _availableSlots.value = times
        }
    }
}
