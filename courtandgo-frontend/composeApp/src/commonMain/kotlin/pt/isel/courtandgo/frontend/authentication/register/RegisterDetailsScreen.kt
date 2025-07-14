package pt.isel.courtandgo.frontend.authentication.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.courtandgo.frontend.authentication.AuthUiState
import pt.isel.courtandgo.frontend.authentication.AuthViewModel
import pt.isel.courtandgo.frontend.authentication.countryPhoneCode
import pt.isel.courtandgo.frontend.authentication.isValidName
import pt.isel.courtandgo.frontend.authentication.isValidPhoneNumber
import pt.isel.courtandgo.frontend.components.dropdownMenu.DropdownMenuField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterDetailsScreen(
    email: String,
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateBack: () -> Unit = { }
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            println("✅ Navegação após sucesso")
            onRegisterSuccess()
        }
    }

    var name by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("+351") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val hasUppercase = password.any { it.isUpperCase() }
    val hasDigit = password.any { it.isDigit() }
    val hasSpecialChar = password.any { !it.isLetterOrDigit() && !it.isWhitespace() }
    val hasMinLength = password.length >= 8

    val allValid = hasUppercase && hasDigit && hasSpecialChar &&
            hasMinLength && isValidName(name) && isValidPhoneNumber(phone, countryCode)

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
            OutlinedButton(onClick = onNavigateBack) {
                Text("← Voltar")
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Court&Go\uD83C\uDFBE", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("O seu email:")
            OutlinedTextField(
                value = email,
                onValueChange = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Text("Nome:")
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("primeiro e último nome") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Text("Contacto:")
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DropdownMenuField(
                    optionsList = countryPhoneCode,
                    selectedOption = countryCode,
                    onOptionSelected = { countryCode = it }
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    placeholder = { Text("912345678") },
                    modifier = Modifier.weight(3f)
                )
            }

            Spacer(Modifier.height(16.dp))

            Text("Palavra-passe:")
            OutlinedTextField(
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

            when (uiState) {
                is AuthUiState.Loading -> CircularProgressIndicator()
                is AuthUiState.Error -> Text("❌ ${(uiState as AuthUiState.Error).message}")
                else -> Unit
            }

            Button(
                onClick = {
                    viewModel.registerWithEmail(
                        name = name,
                        email = email,
                        password = password,
                        countryCode = countryCode,
                        phone = phone
                    )
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
            color = if (valid) Color(0xFF4CAF50) else Color.Red,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = text,
            color = if (valid) Color.Gray else Color.LightGray,
            fontSize = 14.sp
        )
    }
}
