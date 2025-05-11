package pt.isel.courtandgo.frontend.domain

import kotlinx.datetime.LocalDateTime
import com.benasher44.uuid.uuid4

data class Notification(
    val id: String = uuid4().toString(),
    val title: String,
    val message: String,
    val dateTime: LocalDateTime,
    val type: NotificationType = NotificationType.RESERVATION_REMINDER,
    val isRead: Boolean = false
)

enum class NotificationType {
    RESERVATION_REMINDER,
    SYSTEM,
    PROMOTION
}
