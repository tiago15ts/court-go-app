package pt.isel.courtandgo.frontend.authentication.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var userName by mutableStateOf<String?>(null)
        private set

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError = _loginError.asStateFlow()

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoggingIn.value = true
            _loginError.value = null

            try {
                val user = authRepository.loginWithEmail(email, password)
                authRepository.setToken(user.toString()) //todo fix this
                userName = user.name
                onSuccess()
            } catch (e: Exception) {
                _loginError.value = e.message ?: "Erro desconhecido"
            } finally {
                _isLoggingIn.value = false
            }
        }
    }

    fun loginWithGoogle(
        tokenId: String,
        name: String,
        email: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoggingIn.value = true
            _loginError.value = null

            try {
                val user = authRepository.loginWithGoogle(tokenId, name, email)
                userName = user.name
                onSuccess()
            } catch (e: Exception) {
                _loginError.value = e.message ?: "Erro ao usar Google"
            } finally {
                _isLoggingIn.value = false
            }
        }
    }
}
