package pt.isel.courtandgo.frontend.service.http

import io.ktor.client.HttpClient
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import pt.isel.courtandgo.frontend.domain.Reservation
import pt.isel.courtandgo.frontend.service.ReservationService
import pt.isel.courtandgo.frontend.service.http.errors.BadRequestException
import pt.isel.courtandgo.frontend.service.http.errors.NotFoundException
import pt.isel.courtandgo.frontend.service.http.errors.UpdateReservationException
import pt.isel.courtandgo.frontend.service.http.models.reservation.CreateReservationInput
import pt.isel.courtandgo.frontend.service.http.models.reservation.ReservationDTO
import pt.isel.courtandgo.frontend.service.http.models.reservation.UpdateReservationInput
import pt.isel.courtandgo.frontend.service.http.utils.CourtAndGoException
import pt.isel.courtandgo.frontend.service.http.utils.get
import pt.isel.courtandgo.frontend.service.http.utils.post
import pt.isel.courtandgo.frontend.service.http.utils.put

class ReservationServiceHttp(private val client : HttpClient) : ReservationService {
    override suspend fun getReservations(): List<Reservation> {
        return try {
            val response = client.get<List<ReservationDTO>>("/reservations")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw NotFoundException("Reservations not found: ${e.message}", e)
        }
    }

    override suspend fun getReservationById(id: Int): Reservation? {
        return try {
            val response = client.get<ReservationDTO>("/reservations/$id")
            response.toDomain()
        } catch (e: CourtAndGoException) {
            throw NotFoundException("Reservation with ID $id not found: ${e.message}", e)
        }
    }

    override suspend fun getReservationsForPlayer(playerId: Int): List<Reservation> {
        return try {
            val response = client.get<List<ReservationDTO>>("/player/$playerId/reservations")
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw NotFoundException("Reservations for player with ID $playerId not found: ${e.message}", e)
        }
    }

    override suspend fun createReservation(reservation: Reservation): Reservation {
        return try {
            val input = CreateReservationInput(
                courtId = reservation.courtId,
                userId = reservation.playerId,
                startTime = reservation.startTime.toString(),
                endTime = reservation.endTime.toString(),
                estimatedPrice = reservation.estimatedPrice,
                status = reservation.status
            )
            val response = client.post<ReservationDTO>(
                url = "/reservations",
                body = input)
            response.toDomain()
        } catch (e: CourtAndGoException) {
            throw BadRequestException("Error creating reservation: ${e.message}", e)
        }
    }

    override suspend fun updateReservation(reservation: Reservation): Reservation? {
        return try {
            val input = UpdateReservationInput(
                reservationId = reservation.id,
                startTime = reservation.startTime.toString(),
                endTime = reservation.endTime.toString(),
                estimatedPrice = reservation.estimatedPrice,
                status = reservation.status
            )

            val response = client.put<ReservationDTO>(
                url = "/reservations/${reservation.id}",
                body = input
            )

            response.toDomain()
        } catch (e: CourtAndGoException) {
            throw UpdateReservationException(
                "Error updating reservation with ID ${reservation.id}: ${e.message}", e
            )
        }
    }

    override suspend fun cancelReservation(id: Int): Boolean {
        return try {
            val response = client.put<Boolean>("/reservations/$id/cancel")
            response
        } catch (e: CourtAndGoException) {
            throw BadRequestException(
                "Error cancelling reservation with ID $id: ${e.message}", e
            )
        }
    }

    override suspend fun setConfirmedReservation(id: Int): Boolean {
        return try {
            val response = client.post<Boolean>(
                url = "/reservations/$id/confirm",
            )
            response
        } catch (e: CourtAndGoException) {
            throw BadRequestException(
                "Error confirming reservation with ID $id: ${e.message}", e
            )
        }
    }

    override suspend fun getReservationsByCourtIdsAndDate(
        courtIds: List<Int>,
        date: LocalDate
    ): List<Reservation> {
        val courtIdsParam = courtIds.joinToString(",")
        val dateParam = date.toString()

        return try {
            val response = client.get<List<ReservationDTO>>(
                "/reservations/filter?courtIds=$courtIdsParam&date=$dateParam"
            )
            response.map { it.toDomain() }
        } catch (e: CourtAndGoException) {
            throw NotFoundException(
                "Reservations for court IDs $courtIds on date $date not found: ${e.message}", e
            )
        }
    }
}