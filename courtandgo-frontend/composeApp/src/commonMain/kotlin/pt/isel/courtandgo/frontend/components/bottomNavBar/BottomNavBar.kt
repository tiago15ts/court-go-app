package pt.isel.courtandgo.frontend.components.bottomNavBar

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import pt.isel.courtandgo.frontend.components.bottomNavBar.Tab.*

@Composable
fun BottomNavigationBar(
    selectedTab: Tab,
    onTabSelected: (Tab) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            selected = selectedTab == Home,
            onClick = { onTabSelected(Home) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = Color.Gray
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Pesquisar"
                )
            },
            selected = selectedTab == Search,
            onClick = { onTabSelected(Search) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = Color.Gray
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Notifications, // Replace if you add a calendar icon later
                    contentDescription = "Calendário"
                )
            },
            selected = selectedTab == Calendar,
            onClick = { onTabSelected(Calendar) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = Color.Gray
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                )
            },
            selected = selectedTab == Profile,
            onClick = { onTabSelected(Profile) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = Color.Gray
            )
        )
    }
}
