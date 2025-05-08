package pt.isel.courtandgo.frontend.service.mock.repo

import pt.isel.courtandgo.frontend.domain.Reservation

class ReservationRepoMock {
    // Mock implementation of reservation repository methods
    private val reservations = mutableListOf<Reservation>()

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