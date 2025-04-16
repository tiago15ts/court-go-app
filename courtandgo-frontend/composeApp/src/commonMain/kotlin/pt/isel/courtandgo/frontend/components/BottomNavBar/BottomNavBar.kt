package pt.isel.courtandgo.frontend.components.bottomNavBar

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.isel.courtandgo.frontend.components.bottomNavBar.Tab.Calendar
import pt.isel.courtandgo.frontend.components.bottomNavBar.Tab.Home
import pt.isel.courtandgo.frontend.components.bottomNavBar.Tab.Profile
import pt.isel.courtandgo.frontend.components.bottomNavBar.Tab.Search


@Composable
fun BottomNavigationBar(selectedTab: Tab, onTabSelected: (Tab) -> Unit) {
    BottomNavigation {
        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (selectedTab == Home) Color.Black else Color.Gray
                )
            },
            selected = selectedTab == Home,
            onClick = { onTabSelected(Home) },
            selectedContentColor = Color.Green,
            unselectedContentColor = Color.Gray
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Pesquisar",
                    tint = if (selectedTab == Search) Color.Black else Color.Gray
                )
            },
            selected = selectedTab == Search,
            onClick = { onTabSelected(Search) },
            selectedContentColor = Color.Green,
            unselectedContentColor = Color.Gray
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Default.Notifications, //todo fix this
                    contentDescription = "Calendário",
                    tint = if (selectedTab == Calendar) Color.Black else Color.Gray
                )
            },
            selected = selectedTab == Calendar,
            onClick = { onTabSelected(Calendar) },
            selectedContentColor = Color.Green,
            unselectedContentColor = Color.Gray
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Perfil",
                    modifier = Modifier.size(24.dp).clip(CircleShape),
                    tint = if (selectedTab == Profile) Color.Black else Color.Gray
                )
            },
            selected = selectedTab == Profile,
            onClick = { onTabSelected(Profile) },
            selectedContentColor = Color.Green,
            unselectedContentColor = Color.Gray
        )
    }
}

