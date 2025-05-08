package pt.isel.courtandgo.frontend.service.mock

import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.service.ReservationService
import pt.isel.courtandgo.frontend.service.mock.repo.ReservationRepoMock

class MockReservationService(private val repoMock: ReservationRepoMock) : ReservationService {

    override suspend fun getReservations(): List<Reservation> {
        return repoMock.getAllReservations()
    }

    override suspend fun getReservationById(id: Int): Reservation? {
        return repoMock.getReservationById(id)
    }

    override suspend fun createReservation(reservation: Reservation): Reservation {
        return repoMock.createReservation(reservation)
    }

    override suspend fun updateReservation(reservation: Reservation): Reservation? {
        return repoMock.updateReservation(reservation)
    }

    override suspend fun deleteReservation(id: Int): Boolean {
        return repoMock.deleteReservation(id)
    }
}