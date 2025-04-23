package pt.isel.courtandgo.frontend.profile.editProfile

import ProfileAvatar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.isel.courtandgo.frontend.authentication.countryPhoneCode
import pt.isel.courtandgo.frontend.components.datePicker.DatePickerComponent
import pt.isel.courtandgo.frontend.domain.User


@Composable
fun EditProfileScreen(
    user: User,
    onBack: () -> Unit,
    onSave: (User) -> Unit,
    onChangePhoto: () -> Unit
) {
    val id by remember{mutableStateOf(user.id)}
    var name by remember { mutableStateOf(user.name) }
    var email by remember { mutableStateOf(user.email) }
    var phone by remember { mutableStateOf(user.phone) }
    var countryCode by remember { mutableStateOf(user.countryCode) }
    var gender by remember { mutableStateOf(user.gender ?: "") }
    var birthDate by remember { mutableStateOf(user.birthDate ?: "") }
    var description by remember { mutableStateOf(user.description ?: "") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {

        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
            }
            Text("Editar perfil", style = MaterialTheme.typography.h6)
            TextButton(onClick = {
                onSave(User(id, name, email, countryCode, phone, gender, birthDate, description))
            }) {
                Text("Guardar")
            }
        }

        Spacer(Modifier.height(16.dp))

        // Avatar
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            ProfileAvatar(name)
            TextButton(onClick = onChangePhoto) {
                Text("Alterar foto de perfil", color = Color.Blue)
            }
        }

        Spacer(Modifier.height(24.dp))

        Text("Informação pessoal", fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(12.dp))

        // Campos
        CustomTextField("Nome e apelido", name) { name = it }
        CustomTextField("Email", email) { email = it }

        Row {
            var expanded by remember { mutableStateOf(false) }

            Box(modifier = Modifier.weight(1f)) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(countryCode)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    countryPhoneCode.forEach { code ->
                        DropdownMenuItem(onClick = {
                            countryCode = code
                            expanded = false
                        }) {
                            Text(text = code)
                        }
                    }
                }
            }
            Spacer(Modifier.width(8.dp))
            CustomTextField("Telefone", phone, modifier = Modifier.weight(2f)) { phone = it }
        }

        var genderExpanded by remember { mutableStateOf(false) }
        Column {
            Text("Selecione o seu género:", fontWeight = FontWeight.Bold)
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { genderExpanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(gender.ifBlank { "Género" })
                }

                DropdownMenu(
                    expanded = genderExpanded,
                    onDismissRequest = { genderExpanded = false }
                ) {
                    listOf("Masculino", "Feminino").forEach { option ->
                        DropdownMenuItem(onClick = {
                            gender = option
                            genderExpanded = false
                        }) {
                            Text(option)
                        }
                    }
                }
            }
        }


        DatePickerComponent(
            initialDate = birthDate,
            title = "Data de nascimento",
            description = "Selecione a sua data de nascimento",
            pastDates = true,
            onDateChange = { birthDate = it }
        )



        CustomTextField("Descrição", description, singleLine = false) { description = it }
    }
}
