package pt.isel.courtandgo.frontend.service.http.models.user

import kotlinx.serialization.Serializable

@Serializable
data class emailNotificationInput (
    val enabled: Boolean
)
