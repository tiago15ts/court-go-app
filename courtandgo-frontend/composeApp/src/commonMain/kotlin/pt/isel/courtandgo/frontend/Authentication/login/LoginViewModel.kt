package pt.isel.courtandgo.frontend.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.courtandgo.frontend.repository.AuthRepository

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoggingIn = MutableStateFlow(false)
    val isLoggingIn = _isLoggingIn.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError = _loginError.asStateFlow()

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoggingIn.value = true
            _loginError.value = null

            try {
                val token = authRepository.loginWithEmail(email, password)
                authRepository.setToken(token)
                onSuccess()
            } catch (e: Exception) {
                _loginError.value = e.message ?: "Erro desconhecido"
            } finally {
                _isLoggingIn.value = false
            }
        }
    }

    fun loginWithGoogle(tokenId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoggingIn.value = true
            _loginError.value = null

            try {
                authRepository.setToken(tokenId)
                onSuccess()
            } catch (e: Exception) {
                _loginError.value = e.message ?: "Erro ao usar Google"
            } finally {
                _isLoggingIn.value = false
            }
        }
    }
}
