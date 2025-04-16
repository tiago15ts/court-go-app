package pt.isel.courtandgo.frontend.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.courtandgo.frontend.authentication.login.LoginViewModel
import pt.isel.courtandgo.frontend.components.LayoutScreen
import pt.isel.courtandgo.frontend.components.bottomNavBar.Tab
import pt.isel.courtandgo.frontend.repository.AuthViewModel

@Composable
fun HomeScreen(vm :LoginViewModel, //todo fix this param
               ) {

    LayoutScreen{

        Text("Home of Court&Go")

        val name = vm.userName

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Olá${if (!name.isNullOrBlank()) ", $name 👋" else "!"}",
                style = MaterialTheme.typography.h4
            )
        }
    }
}