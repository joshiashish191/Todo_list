package net.softglobe.todolist.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * This is a Factory class for ViewModel. If ViewModel accepts some parameters, then we need this
 * class so that compiler should know how to initialize our ViewModel. There are not much changes
 * in this class, its just a bit of boilerplate code.
 */
class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context) as T
    }
}