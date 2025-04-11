package pt.isel.courtandgo.frontend

actual class Platform actual constructor() {
    actual val name: String = "iOS"
}

actual fun getPlatform(): Platform = Platform()
