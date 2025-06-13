package pt.isel.courtandgo.frontend.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.courtandgo.frontend.domain.User
import pt.isel.courtandgo.frontend.repository.AuthRepository

sealed class ProfileUiState {
    object Idle : ProfileUiState()
    object Loading : ProfileUiState()
    object Success : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}


class ProfileViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    fun loadUser(user: User) {
        _user.value = user
    }

    fun updateField(modify: (User) -> User) {
        _user.value = _user.value?.let(modify)
    }

    fun updateUser() {
        val current = _user.value ?: return
        _uiState.value = ProfileUiState.Loading
        viewModelScope.launch {
            try {
                val updatedUser = authRepository.updateUser(current)
                _user.value = updatedUser
                _uiState.value = ProfileUiState.Success

            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message ?: "Erro ao atualizar perfil")
            }
        }
    }

    fun resetUiState() {
        _uiState.value = ProfileUiState.Idle
    }
}

