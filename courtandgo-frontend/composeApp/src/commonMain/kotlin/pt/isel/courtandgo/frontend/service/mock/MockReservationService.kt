package pt.isel.courtandgo.frontend.service.mock

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
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

    override suspend fun getReservationsForPlayer(playerId: Int): List<Reservation> {
        return repoMock.getAllReservations().filter { it.playerId == playerId }
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

    override suspend fun setConfirmedReservation(id: Int): Boolean {
        return repoMock.setConfirmedReservation(id)
    }

    override suspend fun getReservationsForClubOnDate(
        reservations: List<Reservation>,
        clubCourtIds: List<Int>,
        date: kotlinx.datetime.LocalDate
    ): Map<Int, List<LocalTime>> {
        return repoMock.getReservationsForClubOnDate(reservations, clubCourtIds, date)
    }

    override fun getAvailableTimeSlotsForClub(
        timeSlotsByCourt: Map<Int, List<LocalTime>>,
        occupiedTimesByCourt: Map<Int, List<LocalTime>>
    ): List<LocalTime> {
        return repoMock.getAvailableTimeSlotsForClub(timeSlotsByCourt, occupiedTimesByCourt)
    }
}