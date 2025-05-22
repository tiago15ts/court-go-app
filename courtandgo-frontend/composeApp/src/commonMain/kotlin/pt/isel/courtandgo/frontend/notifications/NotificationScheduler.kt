package pt.isel.courtandgo.frontend.notifications

interface NotificationScheduler {
    fun scheduleReservationReminder(reservationId: String, message: String, triggerAtMillis: Long)
    fun cancelReservationReminder(reservationId: String)
}

expect fun provideNotificationScheduler(): NotificationScheduler
