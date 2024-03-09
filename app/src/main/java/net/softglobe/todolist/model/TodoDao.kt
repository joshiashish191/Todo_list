package net.softglobe.todolist.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

/**
 * This is the DAO (Data Access Object) interface. We only declare methods here and rely on Room
 * to provide implementation with help of annotations. ALl these operations should execute on
 * background thread except the method which returns LiveData. That is managed to be run on main
 * thread by Room.
 * For full Room library tutorial, visit: [this link](https://youtu.be/ln85bqPqDsY?si=g_6Ua66v0kC-VsBH)
 */
@Dao
interface TodoDao {

    @Query("SELECT * FROM TODOITEM")
    fun getTodoList() : LiveData<List<TodoItem>>

    @Upsert
    suspend fun insertTodo(todoItem: TodoItem)

    @Delete
    suspend fun deleteTodo(todoItem: TodoItem)

    @Query("UPDATE todoitem SET isCompleted=:isCompleted WHERE id=:id")
    suspend fun updateTaskCompletionStatus(id : Int, isCompleted : Boolean)
}