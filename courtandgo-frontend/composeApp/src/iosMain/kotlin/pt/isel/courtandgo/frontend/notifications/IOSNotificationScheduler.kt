package pt.isel.courtandgo.frontend.notifications

import platform.UserNotifications.*
import platform.Foundation.*
import kotlinx.cinterop.*

class IOSNotificationScheduler : NotificationScheduler {
    override fun scheduleReservationReminder(reservationId: String, message: String, triggerAtMillis: Long) {
        val timeInterval = (triggerAtMillis.toDouble() - NSDate().timeIntervalSince1970 * 1000) / 1000

        val content = UNMutableNotificationContent()
        content.setBody(message)
        content.setTitle("Lembrete de Reserva")


        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(timeInterval, repeats = false)
        val request = UNNotificationRequest.requestWithIdentifier(
            identifier = reservationId,
            content = content,
            trigger = trigger
        )

        UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(request, withCompletionHandler = null)
    }
}
