package pt.isel.courtandgo.frontend.service.http.models.court

import kotlinx.serialization.Serializable
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.SportTypeCourt

@Serializable
data class CourtDTO(
    val id: Int,
    val name: String,
    val sportTypeCourt: SportTypeCourt,
    val surfaceType: String?,
    val capacity: Int,
    val price: Double,
    val clubId: Int
){
    fun toDomain(): Court = Court(
        id = id,
        name = name,
        sportTypeCourt = sportTypeCourt,
        surfaceType = surfaceType,
        capacity = capacity,
        price = price,
        clubId = clubId
    )

    companion object {
        fun fromDomain(court: Court): CourtDTO = CourtDTO(
            id = court.id,
            name = court.name,
            sportTypeCourt = court.sportTypeCourt,
            surfaceType = court.surfaceType,
            capacity = court.capacity,
            price = court.price,
            clubId = court.clubId
        )
    }
}
