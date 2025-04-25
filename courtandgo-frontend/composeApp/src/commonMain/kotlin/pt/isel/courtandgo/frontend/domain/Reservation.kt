package pt.isel.courtandgo.frontend.domain

data class Reservation(
    val id: Int,
    val courtId: Int,
    val userId: Int,
    val startTime: String,
    val endTime: String,
    val status: String,
    val price: Double
)
