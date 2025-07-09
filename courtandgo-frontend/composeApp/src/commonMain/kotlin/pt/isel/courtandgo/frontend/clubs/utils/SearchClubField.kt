package pt.isel.courtandgo.frontend.clubs.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size.Companion.Zero
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import pt.isel.courtandgo.frontend.clubs.searchClub.ClubSearchUiState
import pt.isel.courtandgo.frontend.clubs.searchClub.SearchClubViewModel


@Composable
fun SearchClubField(viewModel: SearchClubViewModel) {
    var expanded by remember { mutableStateOf(false) }

    val allClubs by viewModel.clubs.collectAsState()
    var localQuery by remember { mutableStateOf("") }

    var textFieldSize by remember { mutableStateOf(Zero) }

    val uiState by viewModel.uiState.collectAsState()

    val suggestions = run {
        val lowercaseQuery = localQuery.lowercase()
        val locationSuggestions = mutableListOf<Pair<String, String>>()
        val clubNames = mutableListOf<String>()

        allClubs.forEach { club ->
            if (club.name.contains(lowercaseQuery, ignoreCase = true)) {
                clubNames.add(club.name)
            }
            if (club.location.district.name.contains(lowercaseQuery, ignoreCase = true)) {
                locationSuggestions.add("district" to club.location.district.name)
            }
            if (club.location.county.contains(lowercaseQuery, ignoreCase = true)) {
                locationSuggestions.add("county" to club.location.county)
            }
            if (club.location.postalCode.contains(lowercaseQuery, ignoreCase = true)) {
                locationSuggestions.add("postalCode" to club.location.postalCode)
            }
            if (club.location.country.name.contains(lowercaseQuery, ignoreCase = true)) {
                locationSuggestions.add("country" to club.location.country.name)
            }
        }

        locationSuggestions.distinct() to clubNames.distinct()
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = localQuery,
            onValueChange = {
                localQuery = it
                expanded = it.isNotBlank()

                viewModel.updateDistrict(null)
                viewModel.updateCounty(null)
                viewModel.updatePostalCode(null)
                viewModel.updateCountry(null)

                viewModel.updateQuery(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    textFieldSize = it.size.toSize()
                }
                .onFocusChanged {
                    if (it.isFocused && localQuery.isNotBlank()) {
                        expanded = true
                    }
                }
                .testTag("searchField")
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(2.dp)
                .clickable {
                    if (localQuery.isNotBlank()) expanded = true
                },
            placeholder = { Text("Pesquisar por nome ou localização") },
            trailingIcon = {
                Icon(imageVector = Icons.Rounded.Search, contentDescription = "Pesquisar")
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        if (uiState is ClubSearchUiState.Error) {
            Text(
                text = (uiState as ClubSearchUiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        AnimatedVisibility(
            visible = expanded &&
                    localQuery.isNotBlank() &&
                    (suggestions.first.isNotEmpty() || suggestions.second.isNotEmpty())
        )
        {
            Card(
                modifier = Modifier
                    .width(textFieldSize.width.dp)
                    .padding(horizontal = 5.dp)
                    .testTag("suggestionsCard"),
                elevation = CardDefaults.cardElevation(15.dp),
            ) {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 200.dp)
                ) {
                    val (locations, clubNames) = suggestions

                    if (locations.isNotEmpty()) {
                        item { Text("Localizações",
                            modifier = Modifier.padding(8.dp), fontSize = 14.sp, fontWeight = FontWeight.Bold) }

                        items(locations) { (type, value) ->
                            SuggestionItem(text = value) {
                                // Limpar todos os filtros
                                viewModel.updateDistrict(null)
                                viewModel.updateCounty(null)
                                viewModel.updatePostalCode(null)
                                viewModel.updateCountry(null)
                                viewModel.updateQuery(null)

                                // Atualizar apenas o campo correspondente
                                when (type) {
                                    "district" -> viewModel.updateDistrict(value)
                                    "county" -> viewModel.updateCounty(value)
                                    "postalCode" -> viewModel.updatePostalCode(value)
                                    "country" -> viewModel.updateCountry(value)
                                }

                                localQuery = value
                                expanded = false
                            }
                        }
                    }

                    if (clubNames.isNotEmpty()) {
                        item { Text("Clubes", modifier = Modifier.padding(8.dp), fontSize = 14.sp, fontWeight = FontWeight.Bold) }
                        items(clubNames) { name ->
                            SuggestionItem(text = name) {
                                viewModel.updateQuery(name)
                                localQuery = name
                                expanded = false
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SuggestionItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Text(text)
    }
}




