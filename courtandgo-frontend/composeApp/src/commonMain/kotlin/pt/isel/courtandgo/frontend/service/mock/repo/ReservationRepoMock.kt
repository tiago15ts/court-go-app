package pt.isel.courtandgo.frontend.service.mock.repo

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus

class ReservationRepoMock {

    private val reservations = mutableListOf<Reservation>(
        Reservation(1, 1, 1, LocalDateTime(2025, 5, 20, 11, 0), LocalDateTime(2025, 5, 20, 13, 0), 25.0),
        Reservation(2, 2, 1, LocalDateTime(2025, 5, 21, 16, 0), LocalDateTime(2025, 5, 21, 17, 0), 12.5),
        Reservation(3, 1, 1, LocalDateTime(2025, 5, 10, 10, 30), LocalDateTime(2025, 5, 10, 13, 0), 23.0, ReservationStatus.CONFIRMED)

    )

    private var currentId = 4

    fun getAllReservations(): List<Reservation> {
        return reservations
    }

    fun getReservationById(id: Int): Reservation? {
        return reservations.find { it.id == id }
    }

    fun createReservation(reservation: Reservation): Reservation {
        val newReservation = reservation.copy(id = currentId++)
        reservations.add(newReservation)
        return newReservation
    }

    fun updateReservation(reservation: Reservation): Reservation? {
        val index = reservations.indexOfFirst { it.id == reservation.id }
        return if (index != -1) {
            reservations[index] = reservation
            reservation
        } else null
    }

    fun deleteReservation(id: Int): Boolean {
        val index = reservations.indexOfFirst { it.id == id }
        return if (index != -1) {
            val existing = reservations[index]
            reservations[index] = existing.copy(status = ReservationStatus.CANCELLED)
            true
        } else {
            false
        }
    }

    fun setConfirmedReservation(id: Int): Boolean {
        val index = reservations.indexOfFirst { it.id == id }
        return if (index != -1) {
            val existing = reservations[index]
            reservations[index] = existing.copy(status = ReservationStatus.CONFIRMED)
            true
        } else {
            false
        }
    }

    fun getReservationsForClubOnDate(
        reservations: List<Reservation>,
        clubCourtIds: List<Int>,
        date: LocalDate
    ): Map<Int, List<LocalTime>> {
        return reservations
            .filter { it.courtId in clubCourtIds && it.startTime.date == date }
            .groupBy { it.courtId }
            .mapValues { entry -> entry.value.map { it.startTime.time } }
    }

    fun getAvailableTimeSlotsForClub(
        timeSlotsByCourt: Map<Int, List<LocalTime>>,
        occupiedTimesByCourt: Map<Int, List<LocalTime>>
    ): List<LocalTime> {
        return timeSlotsByCourt.flatMap { (courtId, timeSlots) ->
            val occupiedTimes = occupiedTimesByCourt[courtId] ?: emptyList()
            timeSlots.filterNot { it in occupiedTimes }
        }.distinct()
    }
}