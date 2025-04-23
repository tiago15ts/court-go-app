package pt.isel.courtandgo.frontend.reservations

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pt.isel.courtandgo.frontend.reservations.courts.CourtCard
import pt.isel.courtandgo.frontend.reservations.utils.SearchByDistrictField
import pt.isel.courtandgo.frontend.reservations.utils.SportToggleButton
import androidx.compose.ui.zIndex


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchCourtScreen(
    onBackClick: () -> Unit,
    onSearch: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var selectedSport by remember { mutableStateOf("Ténis") }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        stickyHeader {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .zIndex(1f)
            ) {

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

                Spacer(modifier = Modifier.height(32.dp))

                SearchByDistrictField { selectedDistrict ->
                    searchText = selectedDistrict
                    onSearch(selectedDistrict)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    SportToggleButton("Ténis", selectedSport == "Ténis") { selectedSport = "Ténis" }
                    SportToggleButton("Padel", selectedSport == "Padel") { selectedSport = "Padel" }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(top = 8.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }
        }
        items(10) {
            Box(modifier = Modifier.zIndex(0f)) {
                CourtCard(
                    name = "Beloura Tennis Academy",
                    location = "Sintra",
                    price = "20 €",
                    hours = listOf("18:00", "18:30", "19:00", "19:30", "20:00")
                )
            }
        }
    }
}