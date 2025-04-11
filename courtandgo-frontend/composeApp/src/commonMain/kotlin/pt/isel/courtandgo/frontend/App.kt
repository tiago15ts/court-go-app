package pt.isel.courtandgo.frontend

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import pt.isel.courtandgo.frontend.authentication.AuthManager
import pt.isel.courtandgo.frontend.authentication.LoginScreen


@Composable
@Preview
fun App(authManager: AuthManager) {
    // Aqui podes passar authManager para os ecrãs
    LoginScreen(authManager = authManager)
}