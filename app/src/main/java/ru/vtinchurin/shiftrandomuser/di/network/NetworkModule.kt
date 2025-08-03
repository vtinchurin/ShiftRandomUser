package ru.vtinchurin.shiftrandomuser.di.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LoggingFormat
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import ru.vtinchurin.shiftrandomuser.core.network.UserService

val networkModule = module {
    single<HttpClient> {
        HttpClient(Android) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
                format = LoggingFormat.OkHttp
            }
            install(ContentNegotiation) {
                json(
                    json = Json { ignoreUnknownKeys = true },
                    contentType = ContentType.Any
                )
            }
            install(DefaultRequest) {
                url(BASE_URL)
            }
        }
    }

    single<UserService> {
        UserService.Base(
            client = get()
        )
    }

}

private const val BASE_URL = "https://randomuser.me/api"