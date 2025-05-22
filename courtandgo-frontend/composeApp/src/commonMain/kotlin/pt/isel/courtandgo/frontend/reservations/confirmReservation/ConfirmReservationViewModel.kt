package pt.isel.courtandgo.frontend.reservations.confirmReservation

import pt.isel.courtandgo.frontend.domain.ReservationStatus
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raedghazal.kotlinx_datetime_ext.minus
import com.raedghazal.kotlinx_datetime_ext.plus
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.notifications.provideNotificationScheduler
import pt.isel.courtandgo.frontend.notifications.scheduleReservationNotification
import pt.isel.courtandgo.frontend.service.ReservationService
import kotlin.time.Duration.Companion.minutes

class ConfirmReservationViewModel(
    private val reservationService: ReservationService
) : ViewModel() {

    private val _durationInMinutes = mutableStateOf(60)
    val durationInMinutes: State<Int> = _durationInMinutes

    fun setDuration(minutes: Int) {
        _durationInMinutes.value = minutes
    }

    private val _isSubmitting = mutableStateOf(false)
    val isSubmitting: State<Boolean> = _isSubmitting

    private val _reservationConfirmed = mutableStateOf<Reservation?>(null)
    val reservationConfirmed: State<Reservation?> = _reservationConfirmed

    fun confirmReservation(
        playerId: Int,
        courtId: Int,
        startDateTime: LocalDateTime,
        pricePerHour : Double
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
            status = ReservationStatus.PENDING
        )

        viewModelScope.launch {
            _isSubmitting.value = true
            val created = reservationService.createReservation(reservation)
            _reservationConfirmed.value = created
            _isSubmitting.value = false

            // Agendar a notificação 24h antes (ou mais próximo possível)
            scheduleReservationNotification(created)
        }

    }

    private fun calculatePrice(durationMinutes: Int, pricePerHour: Double): Double {
        val hours = durationMinutes / 60.0
        return pricePerHour * hours
    }

    fun clearReservationConfirmed() {
        _reservationConfirmed.value = null
    }

    fun confirmReservation(reservation: Reservation) {
        viewModelScope.launch {
            reservationService.setConfirmedReservation(reservation.id)
            _reservationConfirmed.value = reservation
        }
    }
}
