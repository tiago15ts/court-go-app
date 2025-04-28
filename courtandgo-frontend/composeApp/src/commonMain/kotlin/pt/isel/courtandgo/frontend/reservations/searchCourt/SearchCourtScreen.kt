package pt.isel.courtandgo.frontend.reservations.searchCourt

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pt.isel.courtandgo.frontend.reservations.courts.CourtCard
import pt.isel.courtandgo.frontend.reservations.utils.SearchByDistrictField
import pt.isel.courtandgo.frontend.reservations.utils.SportToggleButton

@Composable
fun SearchCourtScreen(
    viewModel: CourtSearchViewModel,
    onBackClick: () -> Unit,
    defaultDistrict: String = ""
) {
    val courts by viewModel.courts.collectAsState()
    val selectedSport by viewModel.selectedSport.collectAsState()
    var initialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchCourts()
    }

    LaunchedEffect(defaultDistrict) {
        if (!initialized && defaultDistrict.isNotBlank()) {
            viewModel.updateDistrict(defaultDistrict)
            initialized = true
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar"
                    )
                }
                Text(
                    text = "Pesquisa de campo",
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 48.dp),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            SearchByDistrictField(defaultDistrict) { selectedDistrict ->
                viewModel.updateDistrict(selectedDistrict)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SportToggleButton("Ténis", selectedSport == "Ténis") {
                    viewModel.updateSport("Ténis")

                }
                SportToggleButton("Padel", selectedSport == "Padel") {
                    viewModel.updateSport("Padel")

                }
            }

            Divider(
                modifier = Modifier.padding(vertical = 12.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }

        items(courts) { court ->
            CourtCard(
                name = court.name,
                location = court.district,
                price = court.price.toString(),
                hours = court.availableHours
            )
        }

        if (courts.isEmpty()) {
            item {
                Text(
                    text = "Nenhum campo encontrado.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}
