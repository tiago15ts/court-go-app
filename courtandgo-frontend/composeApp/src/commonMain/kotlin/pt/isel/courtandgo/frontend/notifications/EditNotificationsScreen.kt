package pt.isel.courtandgo.frontend.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable

import kotlinx.datetime.Clock

@Composable
fun EditNotificationsScreen() {

    Column {
        Text("Soon!")
        val scheduler = provideNotificationScheduler()
        Button(onClick = {
            val triggerAtMillis = Clock.System.now().toEpochMilliseconds() + 10_000 // 10 segundos
            scheduler.scheduleReservationReminder(
                reservationId = "test-id",
                message = "Tens uma reserva dentro de 24h!",
                triggerAtMillis = triggerAtMillis
            )
        }) {
            Text("Testar notificação")
        }
    }

}