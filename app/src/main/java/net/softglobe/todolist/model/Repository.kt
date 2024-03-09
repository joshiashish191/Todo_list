package net.softglobe.todolist.model

import androidx.lifecycle.LiveData
/**
* This class know everything about where the data is stored. When it receives the data request
* from ViewModel, it gives back the data.
* */
class Repository(private val todoDao: TodoDao) {

    fun getTodoList() : LiveData<List<TodoItem>> {
        return todoDao.getTodoList()
    }

    suspend fun upsertTodo(todoItem: TodoItem) {
        todoDao.insertTodo(todoItem)
    }

    suspend fun deleteTodo(todoItem: TodoItem) {
        todoDao.deleteTodo(todoItem)
    }

    suspend fun updateTaskCompletionStatus(id : Int, isCompleted : Boolean) {
        todoDao.updateTaskCompletionStatus(id, isCompleted)
    }

}