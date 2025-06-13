package pt.isel.courtandgo.frontend.notifications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditNotificationsScreen(
    viewModel: NotificationSettingsViewModel
) {
    val pushNotificationsEnabled by viewModel.notificationsEnabled
    //val emailNotificationsEnabled by viewModel.emailNotificationsEnabled
    //val smsNotificationsEnabled by viewModel.smsNotificationsEnabled

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


        Text(
            text = "As notificações por push devem ser ativadas nas configurações do dispositivo.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Switch de notificações por Email
        NotificationSettingItem(
            label = "Notificações por Email",
            checked = false,
            onToggle = { /*viewModel.setEmailNotificationsEnabled(it)*/ },

        )

        // Switch de notificações por SMS
        NotificationSettingItem(
            label = "Notificações por SMS",
            checked = false,
            onToggle = { /*viewModel.setSmsNotificationsEnabled(it)*/ },

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("As notificações são enviadas 24 horas antes do início da reserva.")
    }
}

@Composable
private fun NotificationSettingItem(
    label: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onToggle(!checked) }
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(
            checked = checked,
            onCheckedChange = { onToggle(it) },
            enabled = enabled
        )
    }
}

