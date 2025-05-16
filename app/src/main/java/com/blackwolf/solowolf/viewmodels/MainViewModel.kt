package com.blackwolf.solowolf.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blackwolf.solowolf.models.User
import com.blackwolf.solowolf.data.DataRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DataRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _dailyTasks = MutableLiveData<List<Task>>()
    val dailyTasks: LiveData<List<Task>> = _dailyTasks

    init {
        loadUserData()
        loadDailyTasks()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val currentUser = repository.getCurrentUser()
            _user.postValue(currentUser)
        }
    }

    private fun loadDailyTasks() {
        viewModelScope.launch {
            val tasks = repository.getDailyTasks()
            _dailyTasks.postValue(tasks)
        }
    }

    fun completeTask(task: Task) {
        viewModelScope.launch {
            repository.completeTask(task)
            loadUserData() // Refresh user data for updated XP
            loadDailyTasks() // Refresh tasks list
        }
    }
}
