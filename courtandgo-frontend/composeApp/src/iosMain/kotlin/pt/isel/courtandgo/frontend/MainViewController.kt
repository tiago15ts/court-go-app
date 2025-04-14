package pt.isel.courtandgo.frontend

import androidx.compose.ui.window.ComposeUIViewController
import pt.isel.courtandgo.frontend.service.mock.CourtAndGoServiceMock

fun MainViewController() = ComposeUIViewController { CourtAndGoApp( courtAndGoService = CourtAndGoServiceMock()) }