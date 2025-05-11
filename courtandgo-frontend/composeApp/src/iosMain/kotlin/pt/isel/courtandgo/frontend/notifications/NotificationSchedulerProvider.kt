package pt.isel.courtandgo.frontend.notifications

actual fun provideNotificationScheduler(): NotificationScheduler {
    return IOSNotificationScheduler()
}