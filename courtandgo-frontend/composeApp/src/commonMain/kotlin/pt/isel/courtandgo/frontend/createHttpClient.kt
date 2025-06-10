package pt.isel.courtandgo.frontend

import io.ktor.client.HttpClient

expect fun createHttpClient(): HttpClient
