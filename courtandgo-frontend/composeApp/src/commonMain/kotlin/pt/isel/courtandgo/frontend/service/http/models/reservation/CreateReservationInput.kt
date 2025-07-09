package pt.isel.courtandgo.frontend.service.http.models.reservation

import kotlinx.serialization.Serializable
import pt.isel.courtandgo.frontend.domain.ReservationStatus

@Serializable
data class CreateReservationInput(
    val courtId: Int,
    val playerId: Int,
    val startTime: String,
    val endTime: String,
    val estimatedPrice: Double,
    val status: ReservationStatus = ReservationStatus.Pending
)