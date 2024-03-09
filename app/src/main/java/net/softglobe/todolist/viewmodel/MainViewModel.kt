package net.softglobe.todolist.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.softglobe.todolist.model.Repository
import net.softglobe.todolist.model.TodoDao
import net.softglobe.todolist.model.TodoDatabase
import net.softglobe.todolist.model.TodoItem

/**
 * This is a ViewModel class. It takes request from view, performs business logic and takes the
 * data from Model. Once results are ready, it publishes with LiveData which then the view observes
 * and updates the UI.
 */
class MainViewModel(context: Context) : ViewModel() {
    private var todoDao : TodoDao
    private var repository : Repository

    init {
        todoDao = TodoDatabase.getInstance(context).todoDao
        repository = Repository(todoDao)
    }

    fun getTodoList() : LiveData<List<TodoItem>> {
        return repository.getTodoList()
    }

    fun upsertTodo(todoItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertTodo(todoItem)
        }
    }

    fun deleteTodo(todoItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todoItem)
        }
    }

    fun markTaskCompleted(id : Int, isCompleted : Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTaskCompletionStatus(id, isCompleted)
        }
    }

}