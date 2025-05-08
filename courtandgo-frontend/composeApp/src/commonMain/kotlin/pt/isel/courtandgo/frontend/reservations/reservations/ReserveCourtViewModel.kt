package pt.isel.courtandgo.frontend.reservations.reservations

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import pt.isel.courtandgo.frontend.service.ScheduleCourtsService

class ReserveCourtViewModel(
    private val scheduleService: ScheduleCourtsService
) : ViewModel() {

    private val _availableTimes = mutableStateOf<List<LocalTime>>(emptyList())
    val availableTimes: State<List<LocalTime>> = _availableTimes

    fun loadTimesForDate(courtId: Int, date: LocalDate) {
        viewModelScope.launch {
            val times = getTimeSlotsForCourt(scheduleService, courtId, date)
            _availableTimes.value = times
        }
    }
}
