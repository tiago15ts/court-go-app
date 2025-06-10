package pt.isel.courtandgo.frontend.service.http.models

import kotlinx.serialization.Serializable
import pt.isel.courtandgo.frontend.domain.Location

@Serializable
data class LocationDTO(
    val id: Int,
    val address: String,
    val county: String,
    val district: String,
    val country: String,
    val postalCode: String,
    val latitude: Double,
    val longitude: Double
) {
    fun toDomain(): Location = Location(
        id, address, county, district, country, postalCode, latitude, longitude
    )

    companion object {
        fun fromDomain(location: Location) = LocationDTO(
            id = location.id,
            address = location.address,
            county = location.county,
            district = location.district,
            country = location.country,
            postalCode = location.postalCode,
            latitude = location.latitude,
            longitude = location.longitude
        )
    }
}
