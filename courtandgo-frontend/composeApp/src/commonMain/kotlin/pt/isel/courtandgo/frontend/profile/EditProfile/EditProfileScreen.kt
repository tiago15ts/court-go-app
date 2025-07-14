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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.isel.courtandgo.frontend.authentication.countryPhoneCode
import pt.isel.courtandgo.frontend.components.datePicker.DatePickerComponent
import pt.isel.courtandgo.frontend.components.dropdownMenu.DropdownMenuField
import pt.isel.courtandgo.frontend.domain.User
import pt.isel.courtandgo.frontend.profile.ProfileUiState
import pt.isel.courtandgo.frontend.profile.ProfileViewModel
import androidx.compose.material.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch


@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    currentUser: User?,
    onBack: () -> Unit,
    onSaved: () -> Unit
) {

    LaunchedEffect(currentUser) {
        currentUser?.let { viewModel.loadUser(it) }
    }
    val user by viewModel.user.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    LaunchedEffect(uiState) {
        when (uiState) {
            is ProfileUiState.Success -> {
                scope.launch {
                    snackbarHostState.showSnackbar("Perfil guardado com sucesso!")
                }
                viewModel.resetUiState()
                onSaved()
            }
            is ProfileUiState.Error -> {
                val errorMsg = (uiState as ProfileUiState.Error).message
                snackbarHostState.showSnackbar("Erro ao guardar: $errorMsg")
                viewModel.resetUiState()
            }
            else -> Unit
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                }
                Text("Editar perfil", style = MaterialTheme.typography.h6)
                TextButton(onClick = {
                    viewModel.updateUser()
                }) {
                    Text("Guardar")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            if (uiState is ProfileUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(scrollState)
            ) {
                Spacer(Modifier.height(16.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    user?.let { ProfileAvatar(it.name) }
                    TextButton(onClick = { /* onChangePhoto() */ }) { //todo
                        Text("Alterar foto de perfil", color = Color.Blue)
                    }
                }

                Spacer(Modifier.height(24.dp))

                Text("Informação pessoal", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))

                user?.let { user1 ->
                    CustomTextField("Nome e apelido", user1.name) { newName ->
                        viewModel.updateField { it.copy(name = newName) }
                    }
                }

                Spacer(Modifier.height(12.dp))

                user?.let { user1 ->
                    CustomTextField("Email", user1.email) { newEmail ->
                        viewModel.updateField { it.copy(email = newEmail) }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    user?.let { user1 ->
                        DropdownMenuField(
                            optionsList = countryPhoneCode,
                            selectedOption = user1.countryCode,
                            onOptionSelected = { newCountryCode ->
                                viewModel.updateField { it.copy(countryCode = newCountryCode) }
                            },
                        )
                    }
                    Spacer(Modifier.width(8.dp))

                    user?.let {
                        CustomTextField(
                            "Telefone",
                            it.phone,
                            modifier = Modifier.weight(2f)
                        ) { newPhone ->
                            viewModel.updateField { it.copy(phone = newPhone) }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Column {
                    /*
                    Text("Selecione o seu distrito:", fontWeight = FontWeight.Bold)
                    DropdownMenuField(
                        optionsList = distritosPortugal,
                        selectedOption = user?.location ?: "Undefined",
                        onOptionSelected = { newLocation ->
                            viewModel.updateField { it.copy(location = newLocation) }
                        },
                        modifier = Modifier.fillMaxWidth(1f)
                    )

                     */
                }


                DatePickerComponent(
                    initialDate = user?.birthDate ?: "",
                    title = "Data de nascimento",
                    description = "Selecione a sua data de nascimento",
                    pastDates = true,
                    onDateChange = { newBirthDate ->
                        viewModel.updateField { it.copy(birthDate = newBirthDate) }
                    }
                )

                Column {
                    Text("Selecione o seu género:", fontWeight = FontWeight.Bold)
                    DropdownMenuField(
                        optionsList = listOf("Masculino", "Feminino"),
                        selectedOption = user?.gender ?: "Undefined",
                        onOptionSelected = { newGender ->
                            viewModel.updateField { it.copy(gender = newGender) }
                        },
                        modifier = Modifier.fillMaxWidth(1f)
                    )
                }

                Spacer(Modifier.height(12.dp))

                CustomTextField(
                    "Peso",
                    (user?.weight ?: "").toString(), singleLine = false
                ) { newWeight ->
                    viewModel.updateField { it.copy(weight = newWeight.toDouble()) }
                }

                CustomTextField(
                    "Altura",
                    (user?.height ?: "").toString(), singleLine = false
                ) { newHeight ->
                    viewModel.updateField { it.copy(height = newHeight.toDouble()) }
                }


            }
        }
    }
}


