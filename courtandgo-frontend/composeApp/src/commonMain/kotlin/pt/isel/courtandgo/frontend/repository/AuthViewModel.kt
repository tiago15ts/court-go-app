package pt.isel.courtandgo.frontend.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    var userName by mutableStateOf<String?>(null)
        private set

    fun setName(name: String) {
        userName = name
    }

    fun logout() {
        userName = null
    }
}
