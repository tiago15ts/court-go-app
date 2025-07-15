package pt.isel.courtandgo.frontend.repository

import kotlinx.datetime.LocalDate
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.repository.interfaces.ReservationRepository
import pt.isel.courtandgo.frontend.service.CourtAndGoService
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException

class ReservationRepositoryImpl(
    private val courtAndGoService: CourtAndGoService
) : ReservationRepository {
    override suspend fun getReservations(): List<Reservation> {
        val reservations = courtAndGoService.reservationService.getReservations()
        return reservations
    }

    override suspend fun getReservationById(id: Int): Reservation {
        val reservation = courtAndGoService.reservationService.getReservationById(id)
            ?: throw CourtAndGoException("Reservation with ID: $id not found")
        return reservation
    }

    override suspend fun getReservationsForPlayer(playerId: Int): List<Reservation> {
        val reservations = courtAndGoService.reservationService.getReservationsForPlayer(playerId)
        return reservations
    }

    override suspend fun createReservation(reservation: Reservation): Reservation {
        val createdReservation = courtAndGoService.reservationService.createReservation(reservation)
        return createdReservation
    }

    override suspend fun updateReservation(reservation: Reservation): Reservation {
        val updatedReservation = courtAndGoService.reservationService.updateReservation(reservation)
        return updatedReservation ?: throw CourtAndGoException("Failed to update reservation with ID: ${reservation.id}")
    }

    override suspend fun cancelReservation(id: Int): Boolean {
        val isCancelled = courtAndGoService.reservationService.cancelReservation(id)
        return isCancelled
    }

    override suspend fun setConfirmedReservation(id: Int): Boolean {
        val isConfirmed = courtAndGoService.reservationService.setConfirmedReservation(id)
        return isConfirmed
    }

    override suspend fun getReservationsByCourtIdsAndDate(
        courtIds: List<Int>,
        date: LocalDate
    ): List<Reservation> {
        val reservations = courtAndGoService.reservationService.getReservationsByCourtIdsAndDate(courtIds, date)
        return reservations
    }
}