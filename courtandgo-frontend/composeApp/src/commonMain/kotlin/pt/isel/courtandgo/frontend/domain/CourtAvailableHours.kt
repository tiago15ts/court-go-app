package pt.isel.courtandgo.frontend.domain

data class CourtAvailableHours(
    val courtId: Int,
    val availableHours: List<String>
)
