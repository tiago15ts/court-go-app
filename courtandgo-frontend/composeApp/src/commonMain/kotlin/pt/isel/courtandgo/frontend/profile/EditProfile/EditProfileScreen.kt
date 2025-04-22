package pt.isel.courtandgo.frontend.profile.editProfile

import ProfileAvatar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.isel.courtandgo.frontend.authentication.countryPhoneCode
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
    val dateRegex = Regex("""\d{2}/\d{2}/\d{4}""")
    val isValidDate = dateRegex.matches(birthDate)

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

        CustomDropdown("Género", gender, options = listOf("Masculino", "Feminino", "Undefined")) {
            gender = it
        }

        OutlinedTextField(
            value = birthDate,
            onValueChange = { birthDate = it },
            label = { Text("Data de nascimento") },
            placeholder = { Text("dd/mm/aaaa") },
            isError = birthDate.isNotBlank() && !isValidDate,
            modifier = Modifier.fillMaxWidth()
        )

        if (birthDate.isNotBlank() && !isValidDate) {
            Text("Formato inválido. Usa dd/mm/aaaa", color = Color.Red)
        }

        CustomTextField("Descrição", description, singleLine = false) { description = it }
    }
}
