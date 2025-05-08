package pt.isel.courtandgo.frontend.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class SpecialSchedule(
    val specialId: Int,
    val courtId: Int,
    val date: LocalDate,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    val working: Boolean               // true = aberto, false = fechado
)