package pt.isel.courtandgo.frontend.service.http.models.reservation

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus

@Serializable
data class ReservationDTO(
    val reservationId: Int,
    val courtId: Int,
    val userId: Int,
    val startTime: String,
    val endTime: String,
    val estimatedPrice: Double,
    val status: ReservationStatus
) {
    fun toDomain(): Reservation = Reservation(
        id = reservationId,
        courtId = courtId,
        playerId = userId,
        startTime = LocalDateTime.parse(startTime),
        endTime = LocalDateTime.parse(endTime),
        estimatedPrice = estimatedPrice,
        status = status
    )

    companion object {
        fun fromDomain(res: Reservation): ReservationDTO = ReservationDTO(
            reservationId = res.id,
            courtId = res.courtId,
            userId = res.playerId,
            startTime = res.startTime.toString(),
            endTime = res.endTime.toString(),
            estimatedPrice = res.estimatedPrice,
            status = res.status
        )
    }
}
