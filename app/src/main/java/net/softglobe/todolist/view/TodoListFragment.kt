package net.softglobe.todolist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import net.softglobe.todolist.R
import net.softglobe.todolist.databinding.FragmentTodoListBinding
import net.softglobe.todolist.view.adapters.TodoListAdapter
import net.softglobe.todolist.viewmodel.MainViewModel
import net.softglobe.todolist.viewmodel.MainViewModelFactory

/**
 * This is the class providing UI for Todo list items.
 */
class TodoListFragment : Fragment() {

    private lateinit var binding : FragmentTodoListBinding
    private lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_todo_list, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(requireActivity())).get(MainViewModel::class.java)

        viewModel.getTodoList().observe(viewLifecycleOwner) {
            binding.rvTodolist.apply {
                adapter = TodoListAdapter(viewModel, requireActivity())
                layoutManager = LinearLayoutManager(requireActivity())
                (binding.rvTodolist.adapter as TodoListAdapter).submitList(it)
            }
        }

        binding.addItemButton.setOnClickListener {
            PopupUtils.showAddEditTodoPopup(null, requireActivity(), viewModel)
        }
    }


}