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
import androidx.compose.ui.geometry.Size.Companion.Zero
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import pt.isel.courtandgo.frontend.clubs.searchClub.SearchClubViewModel


@Composable
fun SearchClubField(viewModel: SearchClubViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val query by viewModel.query.collectAsState()
    val allClubs by viewModel.clubs.collectAsState()

    var textFieldSize by remember { mutableStateOf(Zero) }

    val suggestions = remember(query, allClubs) {
        val lowercaseQuery = query?.lowercase() ?: ""
        val locations = mutableListOf<String>()
        val clubNames = mutableListOf<String>()

        allClubs.forEach { club ->
            if (club.name.contains(lowercaseQuery, ignoreCase = true)) {
                clubNames.add(club.name)
            }
            if (club.location.district.contains(lowercaseQuery, ignoreCase = true)) {
                locations.add(club.location.district)
            }
            if (club.location.county.contains(lowercaseQuery, ignoreCase = true)) {
                locations.add(club.location.county)
            }
            if (club.location.postalCode.contains(lowercaseQuery, ignoreCase = true)) {
                locations.add(club.location.postalCode)
            }
        }

        locations.distinct() to clubNames.distinct()
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = query ?: "",
            onValueChange = {
                viewModel.updateQuery(it)
                expanded = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    textFieldSize = it.size.toSize()
                }
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(2.dp),
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

        AnimatedVisibility(visible = expanded && query?.isNotBlank() == true) {
            Card(
                modifier = Modifier
                    .width(textFieldSize.width.dp)
                    .padding(horizontal = 5.dp),
                elevation = CardDefaults.cardElevation(15.dp),
            ) {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 200.dp)
                ) {
                    val (locations, clubNames) = suggestions

                    if (locations.isNotEmpty()) {
                        item { Text("Localizações", modifier = Modifier.padding(8.dp), fontSize = 14.sp, fontWeight = FontWeight.Bold) }
                        items(locations) { loc ->
                            SuggestionItem(text = loc) {
                                viewModel.updateDistrict(loc)
                                viewModel.updateQuery("")
                                expanded = false
                            }
                        }
                    }

                    if (clubNames.isNotEmpty()) {
                        item { Text("Clubes", modifier = Modifier.padding(8.dp), fontSize = 14.sp, fontWeight = FontWeight.Bold) }
                        items(clubNames) { name ->
                            SuggestionItem(text = name) {
                                viewModel.updateQuery(name)
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




