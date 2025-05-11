package pt.isel.courtandgo.frontend.notifications

import android.content.Context

lateinit var appContext: Context

fun initNotificationScheduler(context: Context) {
    appContext = context
}

actual fun provideNotificationScheduler(): NotificationScheduler {
    return AndroidNotificationScheduler(appContext)
}