package pt.isel.courtandgo.frontend.utils.addEventToCalendar.apple

fun generateAppleCalendarUrl(reservationId: String): String {
    return "https://<O_TEU_DOMINIO>/reservations/$reservationId/ics"
}
