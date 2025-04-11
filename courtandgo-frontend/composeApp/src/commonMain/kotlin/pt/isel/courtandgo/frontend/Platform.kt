package pt.isel.courtandgo.frontend

expect class Platform() {
    val name: String
}

expect fun getPlatform(): Platform
