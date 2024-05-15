package com.example.data.net

import com.example.data.net.transport.TransportHabitInfo
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.InternalAPI
import kotlinx.serialization.Serializable

class ResponseHandler {
    suspend fun habitsFromResponse(response: HttpResponse): List<TransportHabitInfo> {
        return response.body()
    }

    suspend fun UIDFromResponse(response: HttpResponse): String {
        return response.body<UidResponse>().uid
    }

    @OptIn(InternalAPI::class)
    suspend fun responseContent(response: HttpResponse): String {
        return response.content.readRemaining().readText()
    }

    @Serializable
    data class UidResponse(val uid: String)
}