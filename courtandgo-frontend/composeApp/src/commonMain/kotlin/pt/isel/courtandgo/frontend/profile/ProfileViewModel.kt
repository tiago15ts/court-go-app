package pt.isel.courtandgo.frontend.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.isel.courtandgo.frontend.domain.User
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pt.isel.courtandgo.frontend.repository.AuthRepository


class ProfileViewModel(
    private val authRepository: AuthRepository // ou repository, se tiveres
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    fun loadUser(user: User) {
        _user.value = user
    }

    fun updateField(modify: (User) -> User) {
        _user.value = _user.value?.let(modify)
    }

    fun updateUser(onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val current = _user.value ?: return@launch
                val updatedUser = authRepository.updateUser(current)
                _user.value = updatedUser
                onSuccess()

            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}

