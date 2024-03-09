package net.softglobe.todolist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * This is a database class which we create instance and access DAO through this. We defines entities
 * and TypeConverters in this class to let Room know these classes exists and make use of those.
 */
@Database(entities = [TodoItem::class], version = 1)
@TypeConverters(value = [DataTypeConverters::class])
abstract class TodoDatabase : RoomDatabase() {
    abstract val todoDao : TodoDao

    companion object {
        private var instance : TodoDatabase? = null

        fun getInstance(context : Context) : TodoDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(context, TodoDatabase::class.java, "todo.db").build()
            return instance!!
        }
    }
}