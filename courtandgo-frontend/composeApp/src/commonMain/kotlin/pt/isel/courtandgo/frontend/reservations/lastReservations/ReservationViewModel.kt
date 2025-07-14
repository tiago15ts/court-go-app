package pt.isel.courtandgo.frontend.reservations.lastReservations

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import pt.isel.courtandgo.frontend.domain.Club
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus
import pt.isel.courtandgo.frontend.notifications.provideNotificationScheduler
import pt.isel.courtandgo.frontend.service.ClubService
import pt.isel.courtandgo.frontend.service.CourtService
import pt.isel.courtandgo.frontend.service.ReservationService
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException

sealed class ReservationUiState {
    object Idle : ReservationUiState()
    object Loading : ReservationUiState()
    object Success : ReservationUiState()
    data class Error(val message: String) : ReservationUiState()
}


class ReservationViewModel(
    private val reservationService: ReservationService,
    private val clubService: ClubService,
    private val courtService: CourtService
) : ViewModel() {

    private val _uiState = MutableStateFlow<ReservationUiState>(ReservationUiState.Idle)
    val uiState: StateFlow<ReservationUiState> = _uiState.asStateFlow()

    private val _futureReservations = MutableStateFlow<List<Reservation>>(emptyList())
    val futureReservations: StateFlow<List<Reservation>> = _futureReservations.asStateFlow()

    private val _clubNames = mutableStateOf<Map<Int, String>>(emptyMap())
    val clubNames: State<Map<Int, String>> = _clubNames

    private val _pastReservations = MutableStateFlow<List<Reservation>>(emptyList())
    val pastReservations: StateFlow<List<Reservation>> = _pastReservations.asStateFlow()

    fun loadReservations(playerId: Int) {
        viewModelScope.launch {
            _uiState.value = ReservationUiState.Loading
            delay(500)
            try {
                val all = reservationService.getReservationsForPlayer(playerId)
                val now = Clock.System.now()
                val zone = TimeZone.currentSystemDefault()

                _futureReservations.value = all
                    .filter {
                        it.status != ReservationStatus.Cancelled &&
                                it.startTime.toInstant(zone) > now
                    }
                    .sortedBy { it.startTime.toInstant(zone) }

                _pastReservations.value = all
                    .filter {
                        it.status == ReservationStatus.Cancelled ||
                                it.startTime.toInstant(zone) <= now
                    }
                    .sortedByDescending { it.startTime.toInstant(zone) }

                val clubs = clubService.getAllClubs()
                val courtIdToClubName = mutableMapOf<Int, String>()

                clubs.forEach { club ->
                    val courts = courtService.getCourtsByClubId(club.id)
                    courts.forEach { court ->
                        courtIdToClubName[court.id] = club.name
                    }
                }
                _clubNames.value = courtIdToClubName

                _uiState.value = ReservationUiState.Success
            } catch (e: CourtAndGoException) {
                _uiState.value = ReservationUiState.Error("Erro ao carregar reservas: ${e.message}")
            }
            catch (e: Exception) {
                _uiState.value = ReservationUiState.Error("Erro inesperado ao carregar as reservas: ${e.message}")
            }
        }
    }


    fun cancelReservation(reservation: Reservation) {
        val scheduler = provideNotificationScheduler()
        viewModelScope.launch {
            _uiState.value = ReservationUiState.Loading
            try {
                reservationService.cancelReservation(reservation.id)
                scheduler.cancelReservationReminder(reservation.id.toString())
                loadReservations(reservation.playerId)
                _uiState.value = ReservationUiState.Success
            } catch (e: CourtAndGoException) {
                _uiState.value = ReservationUiState.Error("Erro ao cancelar reserva: ${e.message}")
            } catch (e: Exception) {
                _uiState.value = ReservationUiState.Error("Erro inesperado ao cancelar a reserva: ${e.message}")
            }
        }
    }

    suspend fun getClubInfoByCourtId(courtId: Int): Club? {
        val clubId = clubService.getClubIdByCourtId(courtId)
        return clubService.getClubById(clubId)
    }

    suspend fun getCourtInfoByCourtId(courtId: Int): Court {
        return courtService.getCourtById(courtId) ?: throw IllegalArgumentException("Court não encontrado com ID: $courtId")
    }
}
