package com.alfoll.todotracker.data

import kotlinx.coroutines.delay

class FakeTaskRepository {
    // мокаем данные - якобы получаем с сервера
    private val tasks = mutableListOf(
        TaskEntity("1", "Review code"),
        TaskEntity("2", "Learn Compose"),
        TaskEntity("3", "Make coffee"),
        TaskEntity("4", "Go to the gym"),
    )

    // работаем на корутинах
    suspend fun getTasks(): List<TaskEntity> {
        delay(500) // имитируем работу - okhttp.get

        return tasks
    }

    // можно nullable/exception - лучше exception но для простоты nullable будет
    suspend fun getTask(id: String): TaskEntity? {
        delay(500)

        return tasks.firstOrNull { it.id == id }
    }

    // в update возвращать entity лучше всегда
    suspend fun toggleDone(id: String, isDone: Boolean): TaskEntity? {
        delay(500)

        val index = tasks.indexOfFirst { it.id == id }
        if (index < 0) return null

        // меняем и записываем
        val updated = tasks[index].copy(isDone = isDone)
        tasks[index] = updated

        return updated
    }

}