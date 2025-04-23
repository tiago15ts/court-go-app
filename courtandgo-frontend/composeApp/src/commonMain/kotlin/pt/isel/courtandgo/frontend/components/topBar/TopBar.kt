package pt.isel.courtandgo.frontend.components.topBar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourtAndGoTopBar(
    onLeftIconClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = "Court&Go",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            Box {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ) {
                    DropdownMenuItem(
                        text = { Text("Opção 1") },
                        onClick = {
                            showMenu = false
                            onLeftIconClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Opção 2") },
                        onClick = { showMenu = false }
                    )
                }
            }
        },
        actions = {},
        modifier = Modifier.fillMaxWidth()
    )
}
