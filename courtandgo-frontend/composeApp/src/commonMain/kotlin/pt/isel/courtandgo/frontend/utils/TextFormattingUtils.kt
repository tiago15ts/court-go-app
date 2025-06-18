package pt.isel.courtandgo.frontend.utils

import pt.isel.courtandgo.frontend.domain.SportType

fun SportType.toPortugueseName(): String = when (this) {
    SportType.TENNIS -> "Ténis"
    SportType.PADEL -> "Padel"
    SportType.BOTH -> "Ambos"
}
