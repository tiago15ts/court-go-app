package pt.isel.courtandgo.frontend.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SnackbarError(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier,
    ) { data ->
        Snackbar(
            snackbarData = data,
            containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.95f),
            contentColor = MaterialTheme.colorScheme.onError,
            actionColor = MaterialTheme.colorScheme.onError,
            shape = MaterialTheme.shapes.medium
        )
    }
}

