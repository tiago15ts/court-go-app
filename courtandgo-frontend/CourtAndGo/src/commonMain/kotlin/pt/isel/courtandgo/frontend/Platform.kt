package pt.isel.courtandgo.frontend

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform