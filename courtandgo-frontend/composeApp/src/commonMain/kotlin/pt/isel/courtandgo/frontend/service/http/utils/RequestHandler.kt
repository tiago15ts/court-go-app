package pt.isel.courtandgo.frontend.service.http.utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import pt.isel.courtandgo.frontend.service.http.models.Problem
import pt.isel.courtandgo.frontend.service.http.models.ProblemDTO


const val MEDIA_TYPE = "application/json"
val BASE_URL = "/api"
const val TOKEN_TYPE = "Bearer"
const val ERROR_MEDIA_TYPE = "application/problem+json"
const val SCHEME = "bearer"
const val NAME_WWW_AUTHENTICATE_HEADER = "WWW-Authenticate"

open class CourtAndGoException(message: String?, cause: Throwable? = null) : Exception(message, cause)

// -------- GET --------
suspend inline fun <reified T : Any> HttpClient.get(
    url: String,
    token: String = ""
): T {
    val response = get(BASE_URL + url) {
        if (token.isNotEmpty()) header("Authorization", "$TOKEN_TYPE $token")
        header("Content-Type", MEDIA_TYPE)
        header("Accept", "$MEDIA_TYPE, $ERROR_MEDIA_TYPE")
    }
    return response.processResponse()
}

// -------- POST --------
suspend inline fun <reified T : Any> HttpClient.post(
    url: String,
    token: String = "",
    body: Any? = null
): T {
    val response = post(BASE_URL + url) {
        if (token.isNotEmpty()) header("Authorization", "$TOKEN_TYPE $token")
        header("Content-Type", MEDIA_TYPE)
        header("Accept", "$MEDIA_TYPE, $ERROR_MEDIA_TYPE")
        if (body != null) setBody(body)
    }
    return response.processResponse()
}

// -------- PUT --------
suspend inline fun <reified T : Any> HttpClient.put(
    url: String,
    token: String = "",
    body: Any? = null
): T {
    val response = put(BASE_URL + url) {
        if (token.isNotEmpty()) header("Authorization", "$TOKEN_TYPE $token")
        header("Content-Type", MEDIA_TYPE)
        header("Accept", "$MEDIA_TYPE, $ERROR_MEDIA_TYPE")
        if (body != null) setBody(body)
    }
    return response.processResponse()
}

// -------- Processamento da resposta --------
suspend inline fun <reified T : Any> HttpResponse.processResponse(): T {
    try {
        if (this.status.value == 200 && this.headers[HttpHeaders.ContentLength]?.toIntOrNull() == 0) {
            if (T::class == Unit::class) return Unit as T
            throw CourtAndGoException("Expected body but got empty response")
        }

        if (this.headers[NAME_WWW_AUTHENTICATE_HEADER] != null)
            throw CourtAndGoException("Failed to authenticate")

        val contentType = this.headers[HttpHeaders.ContentType] ?: ""

        return when {
            contentType.startsWith(ERROR_MEDIA_TYPE) -> {
                val problem: Problem = this.body<ProblemDTO>().toProblem()
                throw CourtAndGoException(fetchErrorFile(problem.uri))
            }

            contentType.startsWith(MEDIA_TYPE) -> {
                this.body()
            }

            else -> {
                if (this.status.value in listOf(400, 404, 502)) {
                    throw CourtAndGoException("Failed to process request")
                } else {
                    throw CourtAndGoException("Unexpected error")
                }
            }
        }
    } catch (e: Exception) {
        throw CourtAndGoException("Exception while processing response", e)
    }
}