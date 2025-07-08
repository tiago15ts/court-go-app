package pt.isel.courtandgo.frontend.service.http.models.location

import kotlinx.serialization.Serializable
import pt.isel.courtandgo.frontend.domain.District

@Serializable
data class DistrictDTO(
    val id: Int,
    val name: String,
    val countryId: Int
) {
    fun toDomain(): District =
        District(id, name, countryId)

    companion object {
        fun fromDomain(district: District) = DistrictDTO(
            id = district.id,
            name = district.name,
            countryId = district.countryId
        )
    }
}


