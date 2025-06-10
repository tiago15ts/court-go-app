// commonMain
package pt.isel.courtandgo.frontend.service.http.utils

fun fetchErrorFile(url: String): String =
    url.split("/").last().split("-").joinToString(" ").replaceFirstChar { it.uppercase() }
