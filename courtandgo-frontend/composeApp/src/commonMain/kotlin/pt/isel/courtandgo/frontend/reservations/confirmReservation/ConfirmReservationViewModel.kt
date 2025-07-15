package pt.isel.courtandgo.frontend.reservations.confirmReservation

import pt.isel.courtandgo.frontend.domain.ReservationStatus
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raedghazal.kotlinx_datetime_ext.plus
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.notifications.scheduleReservationNotification
import pt.isel.courtandgo.frontend.repository.interfaces.CourtRepository
import pt.isel.courtandgo.frontend.repository.interfaces.ReservationRepository
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException
import kotlin.time.Duration.Companion.minutes


sealed class ConfirmReservationUiState {
    object Idle : ConfirmReservationUiState()
    object Loading : ConfirmReservationUiState()
    data class Success(val reservation: Reservation) : ConfirmReservationUiState()
    data class Error(val message: String) : ConfirmReservationUiState()
}


class ConfirmReservationViewModel(
    private val reservationRepo: ReservationRepository,
    private val courtRepo: CourtRepository
) : ViewModel() {

    private val _uiState = mutableStateOf<ConfirmReservationUiState>(ConfirmReservationUiState.Idle)
    val uiState: State<ConfirmReservationUiState> = _uiState

    private val _durationInMinutes = mutableStateOf(60)
    val durationInMinutes: State<Int> = _durationInMinutes

    private val _courts = mutableStateOf<List<Court>>(emptyList())
    val courts: State<List<Court>> = _courts

    private val _selectedCourtId = mutableStateOf<Int?>(null)
    val selectedCourtId: State<Int?> = _selectedCourtId

    fun setDuration(minutes: Int) {
        _durationInMinutes.value = minutes
    }

    fun setSelectedCourt(courtId: Int) {
        _selectedCourtId.value = courtId
    }

    fun placeReservation(
        playerId: Int,
        courtId: Int,
        startDateTime: LocalDateTime,
        pricePerHour: Double
    ) {
        val duration = durationInMinutes.value
        val endDateTime = startDateTime.plus(duration.minutes)

        val estimatedPrice = calculatePrice(duration, pricePerHour)

        val reservation = Reservation(
            id = 0, // será gerado no mock
            playerId = playerId,
            courtId = courtId,
            startTime = startDateTime,
            endTime = endDateTime,
            estimatedPrice = estimatedPrice,
            status = ReservationStatus.Pending
        )

        viewModelScope.launch {
            _uiState.value = ConfirmReservationUiState.Loading
            try {
                val created = reservationRepo.createReservation(reservation)
                _uiState.value = ConfirmReservationUiState.Success(created)

                // Agendar a notificação 24h antes (ou mais próximo possível)
                scheduleReservationNotification(created)
            } catch (e: CourtAndGoException) {
                _uiState.value = ConfirmReservationUiState.Error(e.message ?: "Erro ao confirmar reserva.")
            } catch ( e: Exception) {
                _uiState.value = ConfirmReservationUiState.Error("Erro inesperado: ${e.message}")
            }
        }
    }

    private fun calculatePrice(durationMinutes: Int, pricePerHour: Double): Double {
        val hours = durationMinutes / 60.0
        return pricePerHour * hours
    }

    fun confirmAfterReservation(reservation: Reservation) {
        viewModelScope.launch {
            _uiState.value = ConfirmReservationUiState.Loading
            try {
                reservationRepo.setConfirmedReservation(reservation.id)
                //_reservationConfirmed.value = reservation
                _uiState.value = ConfirmReservationUiState.Success(reservation)
            } catch (e: CourtAndGoException) {
                _uiState.value =
                    ConfirmReservationUiState.Error(e.message ?: "Erro ao confirmar reserva.")
            } catch (e: Exception) {
                _uiState.value = ConfirmReservationUiState.Error("Erro inesperado: ${e.message}")
            }
        }
    }

    fun loadCourts(clubId: Int, availableCourtsAtTime: Set<Int>) {
        viewModelScope.launch {
            try{
            _courts.value = courtRepo.getCourtsByClubId(clubId)
            // Selecionar o primeiro court disponível nesse horário
            _selectedCourtId.value = _courts.value.firstOrNull { it.id in availableCourtsAtTime }?.id
        } catch (e: CourtAndGoException) {
                _uiState.value = ConfirmReservationUiState.Error(e.message ?: "Erro ao carregar courts.")
            } catch (e: Exception) {
                _uiState.value = ConfirmReservationUiState.Error("Erro inesperado: ${e.message}")
            }
        }
    }

    fun resetUiState() {
        _uiState.value = ConfirmReservationUiState.Idle
    }
}
