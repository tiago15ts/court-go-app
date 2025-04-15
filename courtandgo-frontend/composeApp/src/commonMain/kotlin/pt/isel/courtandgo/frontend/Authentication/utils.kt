package pt.isel.courtandgo.frontend.authentication

fun isValidEmail(email: String): Boolean {
    return Regex("^[A-Za-z](.*)(@)(.+)(\\.)(.+)").matches(email)
}

object AuthConstants {

    // client id from google cloud - CourtAndGoAuth
    const val GOOGLE_SERVER_ID = "238910147216-7oaafnt8q83d0t016afcn6hdt2jb5gqp.apps.googleusercontent.com"
}