package pt.isel.courtandgo.frontend.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.app.PendingIntent
import android.content.Intent
import pt.isel.courtandgo.frontend.MainActivity



class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val message = inputData.getString("message") ?: return Result.failure()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "reservation_reminders"

        // Cria o canal se ainda não existir
        val channel = NotificationChannel(channelId, "Reservation Reminders", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)

        // 👉 Intent para abrir a app (MainActivity)
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Lembrete de Reserva")
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent) // ⬅ torna a notificação clicável
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)

        return Result.success()
    }

}
