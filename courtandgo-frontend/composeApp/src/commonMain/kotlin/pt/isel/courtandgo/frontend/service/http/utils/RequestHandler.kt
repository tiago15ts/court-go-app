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
import pt.isel.courtandgo.frontend.ApiConfig
import pt.isel.courtandgo.frontend.service.http.models.Problem
import pt.isel.courtandgo.frontend.service.http.models.ProblemDTO

open class CourtAndGoException(message: String?, cause: Throwable? = null) : Exception(message, cause)

// -------- GET --------
suspend inline fun <reified T : Any> HttpClient.get(
    url: String,
    token: String = ""

): T {
    val response = get(ApiConfig.BASE_URL + url) {
        if (token.isNotEmpty()) header("Authorization", "${ApiConfig.TOKEN_TYPE} $token")
        header("Content-Type", ApiConfig.MEDIA_TYPE)
        header("Accept", "${ApiConfig.MEDIA_TYPE}, ${ApiConfig.ERROR_MEDIA_TYPE}")
    }
    //println("url: $url")
    //println( "Response status: ${response.status.value}")
    //println("Response body: ${response.body<String>()}")
    return response.processResponse()
}

// -------- POST --------
suspend inline fun <reified T : Any> HttpClient.post(
    url: String,
    token: String = "",
    body: Any? = null
): T {
    val response = post(ApiConfig.BASE_URL + url) {
        if (token.isNotEmpty()) header("Authorization", "${ApiConfig.TOKEN_TYPE} $token")
        header("Content-Type", ApiConfig.MEDIA_TYPE)
        header("Accept", "${ApiConfig.MEDIA_TYPE}, ${ApiConfig.ERROR_MEDIA_TYPE}")
        if (body != null) setBody(body)

    }
    //println( "Response status: ${response.status.value}")
    //println("Response body: ${response.body<String>()}")
    return response.processResponse()
}

// -------- PUT --------
suspend inline fun <reified T : Any> HttpClient.put(
    url: String,
    token: String = "",
    body: Any? = null
): T {
    val response = put(ApiConfig.BASE_URL + url) {
        if (token.isNotEmpty()) header("Authorization", "${ApiConfig.TOKEN_TYPE} $token")
        header("Content-Type", ApiConfig.MEDIA_TYPE)
        header("Accept", "${ApiConfig.MEDIA_TYPE}, ${ApiConfig.ERROR_MEDIA_TYPE}")
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

        if (this.headers[ApiConfig.NAME_WWW_AUTHENTICATE_HEADER] != null)
            throw CourtAndGoException("Failed to authenticate")

        val contentType = this.headers[HttpHeaders.ContentType] ?: ""

        return when {
            contentType.startsWith(ApiConfig.ERROR_MEDIA_TYPE) -> {
                val problem: Problem = this.body<ProblemDTO>().toProblem()
                throw CourtAndGoException(fetchErrorFile(problem.uri))
            }

            contentType.startsWith(ApiConfig.MEDIA_TYPE) -> {
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