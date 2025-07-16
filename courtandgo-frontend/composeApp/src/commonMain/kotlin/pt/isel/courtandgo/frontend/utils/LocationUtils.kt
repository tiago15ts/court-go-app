package pt.isel.courtandgo.frontend.utils

import pt.isel.courtandgo.frontend.domain.Location

fun formatLocationForDisplay(location: Location): String {
    return buildString {
        append(location.address)
        if (location.postalCode.isNotBlank()) append(", ${location.postalCode}")
        if (location.county.isNotBlank()) append(", ${location.county}")
        if (location.district.name.isNotBlank()) append(", ${location.district.name}")
        if (location.country.name.isNotBlank()) append(", ${location.country.name}")
    }
}
