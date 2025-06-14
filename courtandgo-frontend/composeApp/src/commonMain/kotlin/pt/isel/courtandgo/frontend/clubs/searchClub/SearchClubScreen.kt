package pt.isel.courtandgo.frontend.clubs.searchClub


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
import pt.isel.courtandgo.frontend.clubs.components.ClubCard
import pt.isel.courtandgo.frontend.clubs.utils.SportToggleButton
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import pt.isel.courtandgo.frontend.clubs.utils.SearchClubField
import pt.isel.courtandgo.frontend.domain.SportType
import pt.isel.courtandgo.frontend.utils.dateUtils.currentDate
import pt.isel.courtandgo.frontend.utils.dateUtils.currentTime
import pt.isel.courtandgo.frontend.domain.Club


@Composable
fun SearchClubScreen(
    viewModel: SearchClubViewModel,
    onBackClick: () -> Unit,
    onClubClick: (Club) -> Unit,
) {
    val clubs by viewModel.clubs.collectAsState()
    val selectedSport by viewModel.selectedSport.collectAsState()
    val today = remember { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date }

    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.fetchClubs()
    }

    val clubHours by viewModel.clubHours.collectAsState()

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

            SearchClubField(viewModel)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SportToggleButton(SportType.TENNIS, selectedSport == SportType.TENNIS) {
                    viewModel.updateSport(SportType.TENNIS)

                }
                SportToggleButton(SportType.PADEL, selectedSport == SportType.PADEL) {
                    viewModel.updateSport(SportType.PADEL)

                }
            }

            Divider(
                modifier = Modifier.padding(vertical = 12.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }



        when (uiState) {
            is ClubSearchUiState.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            is ClubSearchUiState.Error -> {
                item {
                    Text(
                        text = (uiState as ClubSearchUiState.Error).message,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red
                    )
                }
            }

            is ClubSearchUiState.Success -> {
                items(clubs) { club ->
                    val hoursForCourt = clubHours[club.id] ?: emptyList()

                    val filteredHours = if (today == currentDate) {
                        hoursForCourt.filter { it > currentTime }
                    } else {
                        hoursForCourt
                    }

                    ClubCard(
                        name = club.name,
                        county = club.location.county,
                        district = club.location.district,
                        price = club.averagePrice.toString(),
                        hours = filteredHours,
                        onClick = { onClubClick(club) }
                    )
                }


                if (clubs.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhum clube encontrado.",
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
            ClubSearchUiState.Idle -> {

            }
        }
    }
}
