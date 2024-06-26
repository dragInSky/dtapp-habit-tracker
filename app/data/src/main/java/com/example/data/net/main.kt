package com.example.data.net

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.net.transport.TransportHabitInfo
import com.example.domain.entities.DateProducer
import io.ktor.http.HttpStatusCode
import io.ktor.util.InternalAPI
import kotlinx.coroutines.runBlocking

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val httpClient = HttpClient()
    val responseHandler = ResponseHandler()

    removeAll(httpClient, responseHandler)

//    testAllMethods(httpClient, responseHandler)
}

fun removeAll(httpClient: HttpClient, responseHandler: ResponseHandler) {
    runBlocking {
        val data = httpClient.getHabits()
        if (data.status == HttpStatusCode.OK) {
            val habits = responseHandler.habitsFromResponse(data)

            for (habit in habits) {
                httpClient.deleteHabit(habit.uid)
            }
        }
    }
}

@OptIn(InternalAPI::class)
@RequiresApi(Build.VERSION_CODES.O)
fun testAllMethods(httpClient: HttpClient, responseHandler: ResponseHandler) {
    val habit = TransportHabitInfo(
        count = 1,
        date = 1,
        description = "description",
        frequency = 1,
        priority = 1,
        title = "title",
        type = 1
    )

    runBlocking {
        val putResponse = httpClient.addOrUpdateHabit(habit)
        if (putResponse.status == HttpStatusCode.OK) {
            println("Habit add successful")
        } else {
            println(responseHandler.responseContent(putResponse))
        }

        if (putResponse.status == HttpStatusCode.OK) {
            val uid = responseHandler.UIDFromResponse(putResponse)
            println("uid is: $uid")

            val date = DateProducer.getIntDate()
            httpClient.habitDone(date, uid)

            val data1 = httpClient.getHabits()
            if (data1.status == HttpStatusCode.OK) {
                val habits = responseHandler.habitsFromResponse(data1)
                println("First get response: $habits")
            } else {
                println(responseHandler.responseContent(data1))
            }

            val deleted = httpClient.deleteHabit(uid)
            if (deleted.status != HttpStatusCode.OK) {
                println(deleted.content.readRemaining().readText())
            }
        } else {
            println(responseHandler.responseContent(putResponse))
        }

        val data2 = httpClient.getHabits()
        if (data2.status == HttpStatusCode.OK) {
            val habits = responseHandler.habitsFromResponse(data2)
            println("Second get response: $habits")
        } else {
            println(responseHandler.responseContent(data2))
        }
    }
}