package pt.isel.courtandgo.frontend.reservations.utils

import courtandgo_frontend.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import pt.isel.courtandgo.frontend.domain.Court
import pt.isel.courtandgo.frontend.domain.SportTypeCourt

fun getCourtImage(court: Court): DrawableResource {
    return when (court.sportTypeCourt) {
        SportTypeCourt.Padel -> Res.drawable.padelCourt
        SportTypeCourt.Tennis -> when (court.surfaceType?.lowercase()) {
            "terra batida" -> Res.drawable.tennisCourt3
            "relva" -> Res.drawable.tennisRelva
            "piso duro" -> Res.drawable.tennisCourt2
            else -> Res.drawable.tennisCourt
        }
    }
}
