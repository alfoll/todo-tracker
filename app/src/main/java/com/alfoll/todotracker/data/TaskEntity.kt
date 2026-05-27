package com.alfoll.todotracker.data

data class TaskEntity(
    val id: String,
    val title: String,
    val isDone: Boolean = false,
)