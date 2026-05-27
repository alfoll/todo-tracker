package com.alfoll.todotracker.presentation

import com.alfoll.todotracker.data.TaskEntity

// "не мучаем пользователя ребилдами" сказал сенсей
data class TasksState(
    val query: String = "", // для поиска
    val tasks: List<TaskEntity> = emptyList(),
    val isLoading: Boolean = false, // чтобы понимать что данные скачиваются а не что нет ничего


    /*
    если надо на экране отобразить какую то ошибку отдельно
    или отобразить доп стейт

    isError
    errorMessage
    noNetwork
    */
)