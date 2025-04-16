package pt.isel.courtandgo.frontend.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import pt.isel.courtandgo.frontend.components.bottomNavBar.BottomNavigationBar
import pt.isel.courtandgo.frontend.components.bottomNavBar.Tab

@Composable
fun LayoutScreen(
    content: @Composable () -> Unit
) {
    val selectedTab = remember { mutableStateOf(Tab.Home) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab.value,
                onTabSelected = { tab ->
                    selectedTab.value = tab
                    when (tab) {
                        Tab.Home -> { /* Do nothing, already on Home */ }
                        Tab.Search -> { /* Navigate to Search */ }
                        Tab.Calendar -> { /* Navigate to Calendar */ }
                        Tab.Profile -> { /* Navigate to Profile */ }
                    }
                }
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