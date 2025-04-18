package pt.isel.courtandgo.frontend.components.topBar
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CourtAndGoTopBar(
    onLeftIconClick: () -> Unit,
) {
    TopAppBar(
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primary,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        var showMenu by remember { mutableStateOf(false) }

        // Left icon (menu)
        IconButton(onClick = { showMenu = !showMenu }) {
            Icon(Icons.Default.Menu, contentDescription = "Menu")
        }

        // Title
        Text(
            text = "Court&Go",
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center
        )


        // Dropdown menu à direita
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            DropdownMenuItem(onClick = {
                showMenu = false
                onLeftIconClick()
            }) {
                Text("Opção 1")
            }
            DropdownMenuItem(onClick = { showMenu = false }) {
                Text("Opção 2")
            }
        }
    }
}
