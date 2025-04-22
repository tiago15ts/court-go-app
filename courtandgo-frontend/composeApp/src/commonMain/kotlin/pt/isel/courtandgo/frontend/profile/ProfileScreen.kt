package pt.isel.courtandgo.frontend.profile

import ProfileAvatar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(
    //viewModel: ProfileViewModel,
    name : String, //todo fix this param
    onEditProfile: () -> Unit,
    onNotifications: () -> Unit,
    onLogout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ProfileAvatar(name)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = name, style = MaterialTheme.typography.h6)
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onEditProfile,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Icon(Icons.Default.Person, contentDescription = "Editar Perfil", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Editar Perfil", color = Color.White)
            }

            Button(
                onClick = onNotifications,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = "Notificações",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Notificações", color = Color.White)
            }
        }

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFFFC1C1),
                contentColor = Color.Red
            ),
            border = BorderStroke(1.dp, Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Terminar Sessão")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Terminar Sessão")
        }
    }
}
