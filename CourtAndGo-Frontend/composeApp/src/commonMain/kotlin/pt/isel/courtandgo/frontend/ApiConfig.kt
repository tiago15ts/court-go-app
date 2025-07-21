package pt.isel.courtandgo.frontend

/**
 * Configuração da API - Altere BASE_URL para o seu URL
 * 
 * Para desenvolvimento local com SST:
 * 1. Execute `sst dev` na pasta courtandgo
 * 2. Copie o URL do API Gateway que aparece no terminal
 * 3. Cole aqui substituindo "YOUR_API_GATEWAY_URL"
 *
 */
object ApiConfig {
    // TODO: ALTERE ESTA URL PARA O SEU URL
    const val BASE_URL = "https://b6qelwvee9.execute-api.eu-west-3.amazonaws.com"
    
    // Configurações HTTP
    const val MEDIA_TYPE = "application/json"
    const val TOKEN_TYPE = "Bearer"
    const val ERROR_MEDIA_TYPE = "application/problem+json"
    const val SCHEME = "bearer"
    const val NAME_WWW_AUTHENTICATE_HEADER = "WWW-Authenticate"
}
