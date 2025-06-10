package pt.isel.courtandgo.frontend.service.http.models.reservation

import kotlinx.serialization.Serializable
import pt.isel.courtandgo.frontend.domain.ReservationStatus

@Serializable
data class UpdateReservationStatusInput(
    val status: ReservationStatus
)
