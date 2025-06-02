package pt.isel.courtandgo.frontend.service

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import pt.isel.courtandgo.frontend.domain.Reservation

interface ReservationService {
    suspend fun getReservations(): List<Reservation>
    suspend fun getReservationById(id: Int): Reservation?
    suspend fun getReservationsForPlayer(playerId: Int): List<Reservation>
    suspend fun createReservation(reservation: Reservation): Reservation
    suspend fun updateReservation(reservation: Reservation): Reservation?
    suspend fun deleteReservation(id: Int): Boolean
    suspend fun setConfirmedReservation(id: Int): Boolean
    suspend fun getReservationsForClubOnDate(
        reservations: List<Reservation>,
        clubCourtIds: List<Int>,
        date: LocalDate
    ): Map<Int, List<LocalTime>>
    fun getAvailableTimeSlotsForClub(
        timeSlotsByCourt: Map<Int, List<LocalTime>>,
        occupiedTimesByCourt: Map<Int, List<LocalTime>>
    ): List<LocalTime>
}