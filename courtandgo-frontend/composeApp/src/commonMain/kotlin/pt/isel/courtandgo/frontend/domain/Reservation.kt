package pt.isel.courtandgo.frontend.domain

import kotlinx.datetime.LocalDateTime

data class Reservation(
    val id: Int,
    val courtId: Int,
    val playerId: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val estimatedPrice: Double,
    val status: ReservationStatus = ReservationStatus.PENDING
)

enum class ReservationStatus {
    PENDING, CONFIRMED, CANCELLED
}

