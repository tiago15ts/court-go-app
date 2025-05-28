package pt.isel.courtandgo.frontend.domain

data class Court(
    val id: Int,
    val name: String, //e.g. Court 1, Court 2, etc.
    val sportType: SportType,
    val surfaceType: String?,
    val capacity: Int,
    val price: Double,
    val clubId: Int,
)

enum class SportType {
    TENNIS, PADEL, BOTH
}
