package pt.isel.courtandgo.frontend.service.mock.repo

import kotlinx.datetime.LocalDateTime
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.domain.ReservationStatus

class ReservationRepoMock {

    private val reservations = mutableListOf<Reservation>(
        Reservation(1, 1, 1, LocalDateTime(2025, 5, 20, 11, 0), LocalDateTime(2025, 5, 20, 13, 0), 25.0),
        Reservation(2, 1, 1, LocalDateTime(2025, 5, 21, 16, 0), LocalDateTime(2025, 5, 21, 17, 0), 12.5),
        Reservation(1, 1, 1, LocalDateTime(2025, 5, 10, 10, 30), LocalDateTime(2025, 5, 10, 13, 0), 23.0, ReservationStatus.CONFIRMED)

    )

    fun getAllReservations(): List<Reservation> {
        return reservations
    }

    fun getReservationById(id: Int): Reservation? {
        return reservations.find { it.id == id }
    }

    fun createReservation(reservation: Reservation): Reservation {
        reservations.add(reservation)
        return reservation
    }

    fun updateReservation(reservation: Reservation): Reservation? {
        val index = reservations.indexOfFirst { it.id == reservation.id }
        return if (index != -1) {
            reservations[index] = reservation
            reservation
        } else null
    }

    fun deleteReservation(id: Int): Boolean {
        return true //todo fix this

    }
}