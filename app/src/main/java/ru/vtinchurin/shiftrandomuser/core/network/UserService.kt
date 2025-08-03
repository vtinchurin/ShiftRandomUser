package ru.vtinchurin.shiftrandomuser.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ru.vtinchurin.shiftrandomuser.core.network.model.Response

interface UserService {

    suspend fun getUsers(): Response
    class Base(
        private val client: HttpClient,
    ) : UserService {
        override suspend fun getUsers(): Response {
            return client.get {
                parameter(RESULTS, 15)
            }.body<Response>()
        }

        companion object {
            private const val RESULTS = "results"
        }
    }
}