package net.softglobe.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * This is entity/table class. It contains all the fields as columns in table. It must have at
 * least one primary key.
 */
@Entity
data class TodoItem (
    val taskName : String,
    val taskDate : Date,
    val isCompleted : Boolean,
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
)