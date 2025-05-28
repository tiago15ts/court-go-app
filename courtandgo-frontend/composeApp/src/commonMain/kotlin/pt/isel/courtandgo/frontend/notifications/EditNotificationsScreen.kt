package pt.isel.courtandgo.frontend.notifications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.courtandgo.frontend.domain.Reservation

@Composable
fun EditNotificationsScreen(
    viewModel: NotificationSettingsViewModel,
    reservations: List<Reservation>
) {
    val notificationsEnabled by viewModel.notificationsEnabled

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Notificações",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.setNotificationsEnabled(!notificationsEnabled, reservations)
                }
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = if (notificationsEnabled) "Ativadas" else "Desativadas",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = notificationsEnabled,
                onCheckedChange = {
                    viewModel.setNotificationsEnabled(it, reservations)
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (notificationsEnabled)
                "Receberá lembretes 24h antes das suas reservas."
            else
                "As notificações foram desativadas. Não receberá lembretes.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
