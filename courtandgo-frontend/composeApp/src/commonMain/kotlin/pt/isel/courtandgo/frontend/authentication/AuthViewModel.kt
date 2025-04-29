package pt.isel.courtandgo.frontend.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.courtandgo.frontend.domain.User
import pt.isel.courtandgo.frontend.repository.AuthRepository

/**
 * ViewModel for managing authentication (register and login) state and actions.
 */
class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun setCurrentUser(user: User?) {
        _currentUser.value = user
    }


    fun loginWithEmail(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val user = authRepository.loginWithEmail(email, password)
                authRepository.setToken(user.toString()) //todo fix this
                _currentUser.value = user
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message ?: "Erro ao iniciar sessão"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun authenticateWithGoogle(tokenId: String, name: String, email: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val user = authRepository.authenticateGoogle(tokenId, name, email)
                _currentUser.value = user
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message ?: "Erro com Google"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun registerWithEmail(
        email: String,
        name: String,
        countryCode: String,
        phone: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val user = authRepository.registerWithEmail(
                    email,
                    name,
                    countryCode,
                    phone,
                    password
                )
                _currentUser.value = user
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message ?: "Erro ao registar"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            _currentUser.value = null
            onLoggedOut()
        }
    }
}
