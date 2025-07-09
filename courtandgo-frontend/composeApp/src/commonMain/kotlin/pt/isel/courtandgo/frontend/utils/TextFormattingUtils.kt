package pt.isel.courtandgo.frontend.utils

import pt.isel.courtandgo.frontend.domain.SportTypeCourt

fun SportTypeCourt.toPortugueseName(): String = when (this) {
    SportTypeCourt.Tennis -> "Ténis"
    SportTypeCourt.Padel -> "Padel"
}
