package pt.isel.courtandgo.frontend.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.courtandgo.frontend.domain.User
import pt.isel.courtandgo.frontend.repository.interfaces.AuthRepository

open class NotificationSettingsViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _emailNotificationsEnabled = MutableStateFlow(true)
    val emailNotificationsEnabled: StateFlow<Boolean> = _emailNotificationsEnabled

    private val _user = MutableStateFlow<User?>(null)
    open val user: StateFlow<User?> = _user.asStateFlow()

    open fun loadUser(user: User) {
        _user.value = user
    }

    fun setEmailNotificationsEnabled(enabled: Boolean) {
        _emailNotificationsEnabled.value = enabled
        viewModelScope.launch {
            try {
                _user.value?.let { authRepository.emailNotifications(it.id, enabled) }
            } catch (e: Exception) {
                // Tratar erro ao atualizar as configurações
            }
        }
    }

}

