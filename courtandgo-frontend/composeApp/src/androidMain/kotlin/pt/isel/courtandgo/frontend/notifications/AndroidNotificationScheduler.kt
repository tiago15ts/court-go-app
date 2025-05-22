package pt.isel.courtandgo.frontend.notifications

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class AndroidNotificationScheduler(private val context: Context) : NotificationScheduler {

    override fun scheduleReservationReminder(reservationId: String, message: String, triggerAtMillis: Long) {
        val delay = triggerAtMillis - System.currentTimeMillis()

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(workDataOf("message" to message))
                                        //"reservationId" to reservationId))
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    @SuppressLint("ServiceCast")
    override fun cancelReservationReminder(reservationId: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(reservationId.toInt())
    }
}
