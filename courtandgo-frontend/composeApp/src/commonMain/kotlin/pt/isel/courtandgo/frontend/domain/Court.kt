package pt.isel.courtandgo.frontend.domain

import kotlinx.serialization.Serializable

data class Court(
    val id: Int,
    val name: String, //e.g. Court 1, Court 2, etc.
    val sportType: SportType,
    val surfaceType: String?,
    val capacity: Int,
    val price: Double,
    val clubId: Int,
)

@Serializable
enum class SportType {
    TENNIS, PADEL, BOTH
}
