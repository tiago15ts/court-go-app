package pt.isel.courtandgo.frontend.utils.addEventToCalendar.apple

fun generateAppleCalendarUrl(reservationId: String): String {
    return "https://xt3ptyfk0d.execute-api.eu-west-3.amazonaws.com/reservations/$reservationId/ics"
}
