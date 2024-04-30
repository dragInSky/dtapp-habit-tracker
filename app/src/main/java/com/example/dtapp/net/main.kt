package com.example.dtapp.net

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.dtapp.models.DateProducer
import com.example.dtapp.net.transport.TransportHabitInfo
import io.ktor.http.HttpStatusCode
import io.ktor.util.InternalAPI
import kotlinx.coroutines.runBlocking

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val habit = TransportHabitInfo(
        count = 1,
        date = 1,
        description = "description",
        frequency = 1,
        priority = 1,
        title = "title",
        type = 1
    )

    val client = Client()
    val responseHandler = ResponseHandler()

    removeAll(client, responseHandler)

//    testAllMethods(habit, client, responseHandler)
}

fun removeAll(client: Client, responseHandler: ResponseHandler) {
    runBlocking {
        val data = client.getHabits()
        if (data.status == HttpStatusCode.OK) {
            val habits = responseHandler.habitsFromResponse(data)

            for (habit in habits) {
                client.deleteHabit(habit.uid)
            }
        }
    }
}

@OptIn(InternalAPI::class)
@RequiresApi(Build.VERSION_CODES.O)
fun testAllMethods(habit: TransportHabitInfo, client: Client, responseHandler: ResponseHandler) {
    runBlocking {
        val putResponse = client.addOrUpdateHabit(habit)
        if (putResponse.status == HttpStatusCode.OK) {
            println("Habit add successful")
        } else {
            println(responseHandler.responseContent(putResponse))
        }

        if (putResponse.status == HttpStatusCode.OK) {
            val uid = responseHandler.UIDFromResponse(putResponse)
            println("uid is: $uid")

            val date = DateProducer.getIntDate()
            client.habitDone(date, uid)

            val data1 = client.getHabits()
            if (data1.status == HttpStatusCode.OK) {
                val habits = responseHandler.habitsFromResponse(data1)
                println("First get response: $habits")
            } else {
                println(responseHandler.responseContent(data1))
            }

            val deleted = client.deleteHabit(uid)
            if (deleted.status != HttpStatusCode.OK) {
                println(deleted.content.readRemaining().readText())
            }
        } else {
            println(responseHandler.responseContent(putResponse))
        }

        val data2 = client.getHabits()
        if (data2.status == HttpStatusCode.OK) {
            val habits = responseHandler.habitsFromResponse(data2)
            println("Second get response: $habits")
        } else {
            println(responseHandler.responseContent(data2))
        }
    }
}