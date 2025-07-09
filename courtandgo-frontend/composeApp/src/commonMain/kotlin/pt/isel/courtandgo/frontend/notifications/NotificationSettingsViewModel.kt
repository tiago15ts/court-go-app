package pt.isel.courtandgo.frontend.notifications

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus

class NotificationSettingsViewModel : ViewModel() {

    private val _notificationsEnabled = mutableStateOf(true)
    val notificationsEnabled: State<Boolean> = _notificationsEnabled

    fun setNotificationsEnabled(enabled: Boolean, reservations: List<Reservation>) {
        _notificationsEnabled.value = enabled

        val scheduler = provideNotificationScheduler()

        if (!enabled) {
            reservations
                .filter { it.status != ReservationStatus.Cancelled }
                .forEach { scheduler.cancelReservationReminder(it.id.toString()) }
        } else {
            reservations
                .filter { it.status != ReservationStatus.Cancelled }
                .forEach { scheduleReservationNotification(it) }
        }
    }
}

