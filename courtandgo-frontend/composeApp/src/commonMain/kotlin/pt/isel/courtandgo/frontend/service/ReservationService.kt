package pt.isel.courtandgo.frontend.service

import pt.isel.courtandgo.frontend.domain.Reservation

interface ReservationService {
    suspend fun getReservations(): List<Reservation>
    suspend fun getReservationById(id: Int): Reservation?
    suspend fun getReservationsForPlayer(playerId: Int): List<Reservation>
    suspend fun createReservation(reservation: Reservation): Reservation
    suspend fun updateReservation(reservation: Reservation): Reservation?
    suspend fun deleteReservation(id: Int): Boolean
    suspend fun setConfirmedReservation(id: Int): Boolean
}