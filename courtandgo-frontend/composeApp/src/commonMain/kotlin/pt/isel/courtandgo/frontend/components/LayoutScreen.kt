package pt.isel.courtandgo.frontend.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import pt.isel.courtandgo.frontend.Screen
import pt.isel.courtandgo.frontend.components.bottomNavBar.BottomNavigationBar
import pt.isel.courtandgo.frontend.components.bottomNavBar.Tab
import pt.isel.courtandgo.frontend.components.topBar.CourtAndGoTopBar

@Composable
fun LayoutScreen(
    selectedTab: Tab,
    onTabSelected: (Tab) -> Unit,
    currentScreen: Screen,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            CourtAndGoTopBar(
                onLeftIconClick = { /* TODO: ação botão esquerdo */ }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            content()
        }
    }
}