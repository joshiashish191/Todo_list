package net.softglobe.todolist.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.softglobe.todolist.R
import net.softglobe.todolist.databinding.TodoItemBinding
import net.softglobe.todolist.view.PopupUtils
import net.softglobe.todolist.model.TodoItem
import net.softglobe.todolist.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Adapter class to provide support for Todo list
 */
class TodoListAdapter(private val viewModel: MainViewModel, val context: Context) :
    ListAdapter<TodoItem, TodoListAdapter.TodoViewHolder>(TodoDiffUtils()) {
    private lateinit var binding: TodoItemBinding

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(todoItem: TodoItem) {
            binding.checkTodo.text = todoItem.taskName
            binding.checkTodo.isChecked = todoItem.isCompleted
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            binding.todoDate.text = sdf.format(todoItem.taskDate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.todo_item, parent, false)
        return TodoViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
        val currentTodoItem = getItem(position)
        binding.checkTodo.setOnClickListener {
            viewModel.markTaskCompleted(currentTodoItem.id!!, !currentTodoItem.isCompleted)
            if (!currentTodoItem.isCompleted)
                Toast.makeText(context, "Item marked as completed", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Item marked as pending", Toast.LENGTH_SHORT).show()
        }
        binding.deleteTodoBtn.setOnClickListener {
            viewModel.deleteTodo(currentTodoItem)
            Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnLongClickListener {
            PopupUtils.showAddEditTodoPopup(currentTodoItem, context, viewModel)
            return@setOnLongClickListener true
        }
    }

    class TodoDiffUtils : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem == newItem
        }
    }
}
