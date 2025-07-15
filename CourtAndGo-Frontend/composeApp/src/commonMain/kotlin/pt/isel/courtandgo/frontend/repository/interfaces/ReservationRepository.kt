package pt.isel.courtandgo.frontend.repository.interfaces

import kotlinx.datetime.LocalDate
import pt.isel.courtandgo.frontend.domain.Reservation

interface ReservationRepository {
    suspend fun getReservations(): List<Reservation>
    suspend fun getReservationById(id: Int): Reservation?
    suspend fun getReservationsForPlayer(playerId: Int): List<Reservation>
    suspend fun createReservation(reservation: Reservation): Reservation
    suspend fun updateReservation(reservation: Reservation): Reservation?
    suspend fun cancelReservation(id: Int): Boolean
    suspend fun setConfirmedReservation(id: Int): Boolean
    suspend fun getReservationsByCourtIdsAndDate(
        courtIds: List<Int>,
        date: LocalDate
    ): List<Reservation>
}