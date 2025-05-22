package pt.isel.courtandgo.frontend.notifications

import kotlinx.datetime.*
import pt.isel.courtandgo.frontend.domain.Reservation
import kotlin.time.Duration.Companion.hours

fun scheduleReservationNotification(reservation: Reservation) {
    val scheduler = provideNotificationScheduler()

    val zone = TimeZone.currentSystemDefault()
    val startInstant = reservation.startTime.toInstant(zone)
    val now = Clock.System.now()

    val triggerInstant = startInstant.minus(24.hours)
    val triggerAtMillis = triggerInstant.toEpochMilliseconds()


    val safeTriggerAtMillis = if (triggerAtMillis > now.toEpochMilliseconds()) {
        triggerAtMillis
    } else {
        now.toEpochMilliseconds() + 10_000
    }

    val localStartTime = startInstant.toLocalDateTime(zone)
    val hourFormatted = localStartTime.hour.toString().padStart(2, '0')
    val minuteFormatted = localStartTime.minute.toString().padStart(2, '0')

    val today = now.toLocalDateTime(zone).date
    val reservationDate = localStartTime.date

    val message = when (reservationDate) {
        today -> "Tens uma reserva hoje às ${hourFormatted}h${minuteFormatted}!"
        today.plus(DatePeriod(days = 1)) -> "Tens uma reserva amanhã às ${hourFormatted}h${minuteFormatted}!"
        else -> "Tens uma reserva em breve!"
    }

    scheduler.scheduleReservationReminder(
        reservationId = reservation.id.toString(),
        message = message,
        triggerAtMillis = safeTriggerAtMillis
    )
}

