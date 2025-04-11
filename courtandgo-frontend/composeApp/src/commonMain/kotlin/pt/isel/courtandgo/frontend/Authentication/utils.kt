package pt.isel.courtandgo.frontend.authentication

fun isValidEmail(email: String): Boolean {
    return Regex("^[A-Za-z](.*)(@)(.+)(\\.)(.+)").matches(email)
}

