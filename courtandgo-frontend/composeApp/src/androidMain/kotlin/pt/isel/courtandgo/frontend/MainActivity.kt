package pt.isel.courtandgo.frontend

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.courtandgo.frontend.authentication.AndroidAuthManager

class MainActivity : ComponentActivity() {

    private val authManager = AndroidAuthManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CourtAndGoApp() // Ou passa authManager via DI/ViewModel, etc.
        }

        // Captura redirecionamento se a app for aberta com o token já no URI
        intent?.data?.let { uri ->
            authManager.handleRedirect(uri.toString())
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { uri ->
            authManager.handleRedirect(uri.toString())
        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    //App()
}