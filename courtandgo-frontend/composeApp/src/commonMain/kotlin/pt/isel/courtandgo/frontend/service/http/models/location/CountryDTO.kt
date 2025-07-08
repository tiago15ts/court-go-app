package pt.isel.courtandgo.frontend.service.http.models.location

import kotlinx.serialization.Serializable
import pt.isel.courtandgo.frontend.domain.Country

@Serializable
data class CountryDTO (
    val id: Int,
    val name: String
) {
    fun toDomain() = Country(id, name)

    companion object {
        fun fromDomain(country: Country) = CountryDTO(
            id = country.id,
            name = country.name
        )
    }
}
