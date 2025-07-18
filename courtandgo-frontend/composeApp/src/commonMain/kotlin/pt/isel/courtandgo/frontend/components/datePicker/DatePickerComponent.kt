package pt.isel.courtandgo.frontend.components.datePicker

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(initialDate: String,
                        title: String,
                        description: String,
                        pastDates: Boolean,
                        onDateChange: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    val parsedInitialDate = parseDisplayDate(initialDate)
    var selectedDate by remember { mutableStateOf(parsedInitialDate) }

    LaunchedEffect(initialDate) {
        selectedDate = parseDisplayDate(initialDate)
    }

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DatePickerCard(
            title = title,
            description = description,
            date = selectedDate?.formatToDisplay().orEmpty(),
            buttonText = "Mudar data",
            onClick = { showDatePicker = true }
        )
    }

    if (showDatePicker) {
        val initialMillis = selectedDate
            ?.atStartOfDayIn(TimeZone.currentSystemDefault())
            ?.toEpochMilliseconds()

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialMillis,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val date = Instant.fromEpochMilliseconds(utcTimeMillis)
                        .toLocalDateTime(TimeZone.currentSystemDefault()).date
                    return validateDate(
                        date,
                        if (pastDates) RestrictionType.None else RestrictionType.NoPastDates
                    )
                }
            }
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedMillis = datePickerState.selectedDateMillis
                        if (selectedMillis != null) {
                            val date = Instant.fromEpochMilliseconds(selectedMillis)
                                .toLocalDateTime(TimeZone.currentSystemDefault()).date
                            if (!validateDate(date, if (pastDates) RestrictionType.None else RestrictionType.NoPastDates)) {
                                showError = true
                            } else {
                                selectedDate = date
                                onDateChange(date.formatToDisplay())
                                showDatePicker = false
                                showError = false
                            }
                        }
                    }
                ) {
                    Text("OK", color = Color(0xFF3F51B5))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Fechar", color = Color(0xFF3F51B5))
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = Color(0xFF3F51B5)
            )
        ) {
            Column {
                DatePicker(
                    state = datePickerState,
                    headline = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF3F51B5),
                            modifier = Modifier.padding(16.dp)
                        )
                    },
                    modifier = Modifier
                )
                if (showError) {
                    Text(
                        text = "A data selecionada não é válida. Por favor, escolha outra data.",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DatePickerCard(
    title: String,
    description: String,
    date: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            if (date.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Sua data: $date",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = onClick,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(buttonText, fontSize = 14.sp)
            }

        }
    }
}

private fun validateDate(date: LocalDate, restrictionType: RestrictionType): Boolean {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    return when (restrictionType) {
        RestrictionType.NoPastDates -> date >= today // No past dates
        else -> true // Allow all for None
    }
}

enum class RestrictionType {
    None, NoPastDates
}
