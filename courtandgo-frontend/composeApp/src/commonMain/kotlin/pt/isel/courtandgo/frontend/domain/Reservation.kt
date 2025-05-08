package pt.isel.courtandgo.frontend.domain

data class Reservation(
    val id: Int,
    val courtId: Int,
    val playerId: Int,
    val startTime: String,
    val endTime: String,
    val estimatedPrice: Double,
    val status: ReservationStatus = ReservationStatus.PENDING
)

enum class ReservationStatus {
    PENDING, CONFIRMED, CANCELLED
}

