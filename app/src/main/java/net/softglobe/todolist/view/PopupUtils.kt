package net.softglobe.todolist.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import net.softglobe.todolist.R
import net.softglobe.todolist.model.TodoItem
import net.softglobe.todolist.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Utility class for showing add or edit Todo item popup.
 * It is singleton class.
 */
object PopupUtils {
    fun showAddEditTodoPopup(todoItem: TodoItem?, context: Context, viewModel: MainViewModel) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_todo, null)
        val newItemName = view.findViewById<EditText>(R.id.new_item_name)
        val newItemDate = view.findViewById<TextView>(R.id.new_item_date)
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        if (todoItem != null) {
            newItemName.setText(todoItem.taskName)
            newItemDate.text = sdf.format(todoItem.taskDate)
        }

        val calendar = Calendar.getInstance()
        newItemDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                context,
                { view: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    // Update the calendar with the selected date
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val formattedDate = sdf.format(calendar.time)
                    newItemDate.text = formattedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        var dialogTitle = "Add new Todo"
        if (todoItem != null)
            dialogTitle = "Edit Todo"
        AlertDialog.Builder(context)
            .setTitle(dialogTitle)
            .setPositiveButton("Add") { dialog, position ->
                if (!TextUtils.isEmpty(newItemName.text) && !TextUtils.isEmpty(newItemDate.text)) {
                    val newTodoNameString = newItemName.text.toString()
                    val newTodoDateString = newItemDate.text.toString()
                    viewModel.upsertTodo(
                        TodoItem(
                            newTodoNameString,
                            sdf.parse(newTodoDateString)!!,
                            todoItem?.isCompleted ?: false,
                            todoItem?.id
                        )
                    )
                    if (todoItem == null)
                        Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "Item Modified", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please enter all details", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .setView(view)
            .show()
    }
}