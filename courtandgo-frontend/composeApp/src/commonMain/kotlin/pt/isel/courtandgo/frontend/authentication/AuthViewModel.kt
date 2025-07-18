package pt.isel.courtandgo.frontend.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.courtandgo.frontend.domain.User
import pt.isel.courtandgo.frontend.repository.interfaces.AuthRepository
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException


sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val user: User?) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}


/**
 * ViewModel for managing authentication (register and login) state and actions.
 */
class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    /*
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

     */

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun setCurrentUser(user: User?) {
        _uiState.value = AuthUiState.Success(user)
    }

    fun clearError() {
        _uiState.value = AuthUiState.Idle
    }

    fun setError(message: String) {
        _uiState.value = AuthUiState.Error(message)
    }



    fun loginWithEmail(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val user = authRepository.loginWithEmail(email, password)
                authRepository.setToken(TokenHolder.accessToken)
                _uiState.value = AuthUiState.Success(user)
                onSuccess()
            } catch (e: CourtAndGoException) {
                _uiState.value = AuthUiState.Error(e.message ?: "Erro ao iniciar sessão")
            }
            catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Erro inesperado ao iniciar sessão")
            }
        }
    }

    fun authenticateWithGoogle(tokenId: String, name: String, email: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                TokenHolder.accessToken = tokenId
                val user = authRepository.authenticateGoogle(tokenId, name, email)
                _uiState.value = AuthUiState.Success(user)
                onSuccess()
            } catch (e: CourtAndGoException) {
                _uiState.value = AuthUiState.Error(e.message ?: "Erro ao autenticar com Google")
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Erro inesperado ao autenticar com Google")
            }
        }
    }

    fun registerWithEmail(
        email: String,
        name: String,
        countryCode: String,
        phone: String,
        password: String,
        //onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val user = authRepository.registerWithEmail(
                    email,
                    name,
                    countryCode,
                    phone,
                    password
                )
                _uiState.value = AuthUiState.Success(user)
                //onSuccess()
            } catch (e: CourtAndGoException) {
                _uiState.value = AuthUiState.Error(e.message ?: "Erro ao registar")
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Erro inesperado ao registar")
            }
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.value = AuthUiState.Success(null)
            onLoggedOut()
        }
    }
}
