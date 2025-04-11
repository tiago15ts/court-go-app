package pt.isel.courtandgo.frontend.authentication.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterDetailsScreen(
    email: String,
    onRegister: (String, String, String, String) -> Unit, // email, nome, contacto, password
    onNavigateBack: () -> Unit = { }
) {
    var name by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val hasUppercase = password.any { it.isUpperCase() }
    val hasDigit = password.any { it.isDigit() }
    val hasSpecialChar = password.any { !it.isLetterOrDigit() && !it.isWhitespace() }
    val hasMinLength = password.length >= 8

    val allValid = hasUppercase && hasDigit && hasSpecialChar &&
            hasMinLength && name.isNotBlank() && contact.isNotBlank()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botão de voltar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onNavigateBack) {
                Text("← Voltar")
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Court&Go", style = MaterialTheme.typography.h4)
        Spacer(Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("O seu email:")
            TextField(
                value = email,
                onValueChange = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Text("Nome:")
            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("primeiro e último nome") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Text("Contacto:") //TODO verificar se é um número válido
            TextField(
                value = contact,
                onValueChange = { contact = it },
                placeholder = { Text("+351 912345678") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Text("Palavra-passe:")
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("palavra-passe") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            PasswordRequirement("mínimo de 8 caracteres", hasMinLength)
            PasswordRequirement("um número", hasDigit)
            PasswordRequirement("uma letra maiúscula", hasUppercase)
            PasswordRequirement("um carácter especial", hasSpecialChar)

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    onRegister(email, name, contact, password)
                },
                enabled = allValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Criar a sua conta")
            }
        }
    }
}

@Composable
fun PasswordRequirement(text: String, valid: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = if (valid) "✔" else "❌",
            color = if (valid) Companion.Green else Companion.Red,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = text,
            color = if (valid) Companion.Gray else Companion.LightGray,
            fontSize = 14.sp
        )
    }
}
