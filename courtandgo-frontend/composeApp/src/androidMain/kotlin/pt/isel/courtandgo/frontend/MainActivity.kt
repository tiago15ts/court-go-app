package pt.isel.courtandgo.frontend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import pt.isel.courtandgo.frontend.authentication.AndroidAuthManager
import pt.isel.courtandgo.frontend.dateUtils.initCalendarContext
import pt.isel.courtandgo.frontend.notifications.initNotificationScheduler
import pt.isel.courtandgo.frontend.service.http.CourtAndGoServiceHttp
import pt.isel.courtandgo.frontend.service.mock.CourtAndGoServiceMock
import pt.isel.courtandgo.frontend.utils.addEventToCalendar.CalendarLinkOpener


class MainActivity : ComponentActivity() {

    private val authManager = AndroidAuthManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNotificationScheduler(this)
        initCalendarContext(this)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            if (checkSelfPermission(permission) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(permission), 1)
            }
        }

        setContent {
            //CourtAndGoTheme {

            val httpClient = createHttpClient()
            val serviceMock = CourtAndGoServiceMock()
            val serviceHTTP = CourtAndGoServiceHttp(httpClient)

            val calendarOpener = AndroidCalendarLinkOpener(this)
                CourtAndGoApp(courtAndGoService = serviceHTTP,
                    calendarLinkOpener = calendarOpener)
            //}
        }

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

    class AndroidCalendarLinkOpener(private val context: Context) : CalendarLinkOpener {
        override fun openCalendarLink(url: String) {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    //App()
}