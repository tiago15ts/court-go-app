package pt.isel.courtandgo.frontend.authentication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.core.net.toUri

class AndroidAuthManager(private val context: Context) : AuthManager {

    private val clientId = "1fh5i2j79qsdbqihk2lmo0q6q6"
    private val domain = "eu-west-36c1aj3oez.auth.eu-west-3.amazoncognito.com"
    private val redirectUri = "courtandgo://callback"
    private var token: String? = null

    override fun login() {
        val url = "https://$domain/login?" +
                "response_type=token&client_id=$clientId&redirect_uri=$redirectUri"
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(intent)
    }

    override fun handleRedirect(uri: String) {
        val parsed = uri.toUri()
        val fragment = parsed.fragment
        token = fragment
            ?.split("&")
            ?.find { it.startsWith("id_token=") }
            ?.substringAfter("=")

        println("Token gerado: $token")

        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
            .edit() {
                putString("token", token)
            }
    }

    override fun logout() {
        val url = "https://$domain/logout?client_id=$clientId&logout_uri=$redirectUri"
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(intent)
    }

    override fun isLoggedIn() = token != null
    override fun getToken() =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE).getString("token", null)
}
