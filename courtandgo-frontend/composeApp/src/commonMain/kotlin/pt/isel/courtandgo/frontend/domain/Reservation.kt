package pt.isel.courtandgo.frontend.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

data class Reservation(
    val id: Int,
    val courtId: Int,
    val playerId: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val estimatedPrice: Double,
    val status: ReservationStatus = ReservationStatus.Pending
)

@Serializable
enum class ReservationStatus {
    Pending, Confirmed, Cancelled
}

