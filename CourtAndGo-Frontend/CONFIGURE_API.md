# Configuração da Aplicação Mobile - CourtAndGo

## Configurar URL da API

Para conectar a aplicação mobile ao seu backend:

1. **Abra o ficheiro**: `CourtAndGo-Frontend/composeApp/src/commonMain/kotlin/pt/isel/courtandgo/frontend/service/http/utils/ApiConfig.kt`

2. **Substitua** `"YOUR_API_GATEWAY_URL"` pelo seu URL real:

```kotlin
object ApiConfig {
    // Altere esta linha com o seu URL
    const val BASE_URL = "https://xxxxx.execute-api.eu-west-3.amazonaws.com"
}
```

## URLs de Exemplo

### Desenvolvimento Local (SST Dev)
```kotlin
const val BASE_URL = "https://xxxxx.execute-api.localhost.localstack.cloud"
```

### AWS Produção
```kotlin  
const val BASE_URL = "https://b6qelwvee9.execute-api.eu-west-3.amazonaws.com"
```

## Como Obter o URL

### Para SST Dev (Local)
1. Execute `cd courtandgo && npm run dev`
2. Copie o URL que aparece no terminal
3. Cole no `ApiConfig.kt`

### Para AWS Produção (NÃO FOI TESTADO) 
1. Execute `cd courtandgo && npm run deploy`
2. Use o URL do output do deployment
3. Cole no `ApiConfig.kt`

## Testar Conexão

Após configurar, execute a aplicação mobile e verifique se consegue:
- Fazer login/registo
- Ver lista de clubes
- Fazer reservas

Se houver erros de conexão, verifique se o URL está correto e se o backend está em execução.
