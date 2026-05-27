package com.alfoll.todotracker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfoll.todotracker.data.FakeTaskRepository
import com.alfoll.todotracker.data.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksViewModel(
    private val repository: FakeTaskRepository // вообще надо инерфейс и прокидывать все через DI
): ViewModel() {

    // стейты котлин flow
    // по умолчанию когда создается view модель говорим что данные грузятся
    // state меняет только view model
    private val _uiState = MutableStateFlow(
        TasksState(
            isLoading = true
        )
    )

    // такая запись чтобы отдавать наружу без возможности изменения
    // из ui напрямую менять стейт нельзя (экран не может менять сам себя)
    val uiState = _uiState.asStateFlow() // есть snapshot - value

    // от функции не ожидаем данные в декларативном подходе - независимость от данных в кач ui
    // отправили ивент что загружаем
    fun load() {
        // запускаем в скоупе корутину
        // при создании модели viewModelScope создается, при удалении - удаляется
        // при перестройках экрана модель выживает
        // для ассинхронного вызова но во вне обычная функция
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val tasks = repository.getTasks() // загружаем задачи
            _uiState.update { it.copy(tasks = tasks, isLoading = false) } // загрузили

            // _uiState.update { it.copy(errorMessage = e.message, isLoading = false) } - если ошибки
        }
    }

    // search bar
    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    // TaskDetailedViewModel - работу с уник таской лучше выносить отдельно

    // так обычно не делают - лучше через состояния
    suspend fun getTask(id: String): TaskEntity? {
        return repository.getTask(id)
    }

    fun saveTask(id: String, isDone: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.toggleDone(id, isDone)
            val tasks = repository.getTasks()
            _uiState.update { it.copy(tasks = tasks, isLoading = false) }
        }
    }
}