package pt.isel.courtandgo.frontend.repository

import pt.isel.courtandgo.frontend.domain.Notification

interface NotificationRepository {
    suspend fun addNotification(notification: Notification)
    suspend fun getUserNotifications(userId: String): List<Notification>
    suspend fun markAsRead(notificationId: String)
}
