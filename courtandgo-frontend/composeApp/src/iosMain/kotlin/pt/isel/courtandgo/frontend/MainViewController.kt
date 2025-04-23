package pt.isel.courtandgo.frontend

import androidx.compose.ui.window.ComposeUIViewController
import pt.isel.courtandgo.frontend.service.mock.CourtAndGoServiceMock
import pt.isel.courtandgo.frontend.ui.CourtAndGoTheme

fun MainViewController() = ComposeUIViewController {
    CourtAndGoTheme {
        CourtAndGoApp(courtAndGoService = CourtAndGoServiceMock())
    }
}