package pt.isel.courtandgo.frontend.service.http.models.reservation

import pt.isel.courtandgo.frontend.domain.ReservationStatus
import kotlinx.serialization.Serializable

@Serializable
data class UpdateReservationInput(
    val reservationId: Int,
    val startTime: String,
    val endTime: String,
    val estimatedPrice: Double,
    val status: ReservationStatus
)
