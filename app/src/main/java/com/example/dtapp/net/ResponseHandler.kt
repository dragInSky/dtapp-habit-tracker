package com.example.dtapp.net

import com.example.dtapp.net.transport.TransportHabitInfo
import io.ktor.client.statement.HttpResponse
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class ResponseHandler {
    @OptIn(InternalAPI::class)
    suspend fun habitsFromResponse(response: HttpResponse): List<TransportHabitInfo> {
        val jsonContent: String = response.content.readRemaining().readText()
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString(jsonContent)
    }

    @OptIn(InternalAPI::class)
    suspend fun UIDFromResponse(response: HttpResponse): String {
        val jsonContent: String = response.content.readRemaining().readText()
        val json = Json { ignoreUnknownKeys = true }
        val jsonObject = json.decodeFromString(JsonObject.serializer(), jsonContent)
        return jsonObject["uid"]!!.jsonPrimitive.content
    }

    @OptIn(InternalAPI::class)
    suspend fun responseContent(response: HttpResponse): String {
        return response.content.readRemaining().readText()
    }
}