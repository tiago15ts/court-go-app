package pt.isel.courtandgo.frontend.clubs.components

import courtandgo_frontend.composeapp.generated.resources.Res
import courtandgo_frontend.composeapp.generated.resources.courts
import courtandgo_frontend.composeapp.generated.resources.padelCourt
import courtandgo_frontend.composeapp.generated.resources.padelCourt2
import courtandgo_frontend.composeapp.generated.resources.padelCourt3
import courtandgo_frontend.composeapp.generated.resources.tennisCourt
import courtandgo_frontend.composeapp.generated.resources.tennisCourt2
import courtandgo_frontend.composeapp.generated.resources.tennisCourt3
import courtandgo_frontend.composeapp.generated.resources.tennisRelva
import org.jetbrains.compose.resources.DrawableResource
import pt.isel.courtandgo.frontend.domain.SportsClub

fun getRandomImageForSportType(type: SportsClub): DrawableResource {
    val options = when (type) {
        SportsClub.Tennis -> listOf(Res.drawable.tennisCourt, Res.drawable.tennisCourt2, Res.drawable.tennisCourt3, Res.drawable.tennisRelva)
        SportsClub.Padel -> listOf(Res.drawable.padelCourt, Res.drawable.padelCourt2, Res.drawable.padelCourt3)
        SportsClub.Both -> listOf(Res.drawable.courts)
    }
    return options.random()
}
