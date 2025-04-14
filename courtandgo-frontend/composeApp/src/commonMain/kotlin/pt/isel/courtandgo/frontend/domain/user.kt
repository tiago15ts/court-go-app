package pt.isel.courtandgo.frontend.domain

data class User (
    val id: Int,
    val name: String,
    val email: String,
    val contact: String,
    //todo more optional fields to be added
)