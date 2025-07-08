package pt.isel.courtandgo.frontend.service.http.models.location

import kotlinx.serialization.Serializable
import pt.isel.courtandgo.frontend.domain.Location

@Serializable
data class LocationDTO(
    val id: Int,
    val address: String,
    val county: String,
    val district: DistrictDTO,
    val country: CountryDTO,
    val postalCode: String,
    val latitude: Double,
    val longitude: Double
) {
    fun toDomain(): Location = Location(
        id, address, county, district.toDomain(), country.toDomain(), postalCode, latitude, longitude
    )

    companion object {
        fun fromDomain(location: Location) = LocationDTO(
            id = location.id,
            address = location.address,
            county = location.county,
            district = DistrictDTO.fromDomain(location.district),
            country = CountryDTO.fromDomain(location.country),
            postalCode = location.postalCode,
            latitude = location.latitude,
            longitude = location.longitude
        )
    }
}
