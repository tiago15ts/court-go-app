package pt.isel.courtandgo.frontend

import androidx.compose.ui.window.ComposeUIViewController
import pt.isel.courtandgo.frontend.service.mock.CourtAndGoServiceMock
import pt.isel.courtandgo.frontend.ui.CourtAndGoTheme
import pt.isel.courtandgo.frontend.utils.dateUtils.CalendarLinkOpener
import pt.isel.courtandgo.frontend.service.http.CourtAndGoServiceHttp
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.UIKit.UIApplication


class IosCalendarLinkOpener : CalendarLinkOpener {
    @OptIn(ExperimentalForeignApi::class)
    override fun openCalendarLink(url: String) {
        val nsUrl = NSURL.URLWithString(url)
        if (nsUrl != null) {
            UIApplication.sharedApplication.openURL(nsUrl)
        }
    }
}



fun MainViewController() = ComposeUIViewController {
    //CourtAndGoTheme {

    val httpClient = createHttpClient()
    val serviceMock = CourtAndGoServiceMock()
    val serviceHTTP = CourtAndGoServiceHttp(httpClient)
    val calendarOpener = IosCalendarLinkOpener()

    CourtAndGoApp(
        courtAndGoService = serviceMock,
        calendarLinkOpener = calendarOpener
    )
    //}
}


