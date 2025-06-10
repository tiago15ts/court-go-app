package pt.isel.courtandgo.frontend.service.http.models

import kotlinx.serialization.Serializable

@Serializable
data class ProblemDTO(
    val type: String
) {
    fun toProblem() = Problem(type)
}
