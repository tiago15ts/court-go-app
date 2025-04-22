package pt.isel.courtandgo.frontend.profile.editProfile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.unit.dp


@Composable
fun CustomTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = singleLine,
        modifier = modifier.padding(vertical = 4.dp)
    )
}
