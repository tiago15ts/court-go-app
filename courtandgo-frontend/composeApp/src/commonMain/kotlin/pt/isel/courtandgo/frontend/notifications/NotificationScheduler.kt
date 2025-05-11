package pt.isel.courtandgo.frontend.notifications

interface NotificationScheduler {
    fun scheduleReservationReminder(reservationId: String, message: String, triggerAtMillis: Long)
}


expect fun provideNotificationScheduler(): NotificationScheduler
