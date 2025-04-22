package pt.isel.courtandgo.frontend.authentication

fun isValidEmail(email: String): Boolean {
    return Regex("^[A-Za-z](.*)(@)(.+)(\\.)(.+)").matches(email)
}

object AuthConstants {

    // client id from google cloud - CourtAndGoAuth
    const val GOOGLE_SERVER_ID = "238910147216-7oaafnt8q83d0t016afcn6hdt2jb5gqp.apps.googleusercontent.com"
}


val countryPhoneCode = listOf(
    "+1",    // USA, Canada
    "+7",    // Russia, Kazakhstan
    "+20",   // Egypt
    "+27",   // South Africa
    "+30",   // Greece
    "+31",   // Netherlands
    "+32",   // Belgium
    "+33",   // France
    "+34",   // Spain
    "+36",   // Hungary
    "+39",   // Italy
    "+40",   // Romania
    "+41",   // Switzerland
    "+43",   // Austria
    "+44",   // United Kingdom
    "+45",   // Denmark
    "+46",   // Sweden
    "+47",   // Norway
    "+48",   // Poland
    "+49",   // Germany
    "+51",   // Peru
    "+52",   // Mexico
    "+53",   // Cuba
    "+54",   // Argentina
    "+55",   // Brazil
    "+56",   // Chile
    "+57",   // Colombia
    "+58",   // Venezuela
    "+60",   // Malaysia
    "+61",   // Australia
    "+62",   // Indonesia
    "+63",   // Philippines
    "+64",   // New Zealand
    "+65",   // Singapore
    "+66",   // Thailand
    "+81",   // Japan
    "+82",   // South Korea
    "+84",   // Vietnam
    "+86",   // China
    "+90",   // Turkey
    "+91",   // India
    "+92",   // Pakistan
    "+93",   // Afghanistan
    "+94",   // Sri Lanka
    "+95",   // Myanmar
    "+98",   // Iran
    "+211",  // South Sudan
    "+212",  // Morocco
    "+213",  // Algeria
    "+216",  // Tunisia
    "+218",  // Libya
    "+220",  // Gambia
    "+221",  // Senegal
    "+223",  // Mali
    "+225",  // Ivory Coast
    "+226",  // Burkina Faso
    "+227",  // Niger
    "+228",  // Togo
    "+229",  // Benin
    "+230",  // Mauritius
    "+231",  // Liberia
    "+232",  // Sierra Leone
    "+233",  // Ghana
    "+234",  // Nigeria
    "+234",  // Nigeria
    "+351",  // Portugal
    "+352",  // Luxembourg
    "+353",  // Ireland
    "+354",  // Iceland
    "+355",  // Albania
    "+356",  // Malta
    "+357",  // Cyprus
    "+358",  // Finland
    "+359"   // Bulgaria
)
