package com.example.testmqtt.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.testmqtt.data.ServerDao
import com.example.testmqtt.data.ServerDatabase
import com.example.testmqtt.model.Server
import com.example.testmqtt.model.Topic
import com.example.testmqtt.repository.ServerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServerViewModel(application: Application):AndroidViewModel(application) {
    val readAllData : LiveData<List<Server>>
    private val repository:ServerRepository

    init {
        val serverDao = ServerDatabase.getDatabase(application).serverDao()
        repository=ServerRepository(serverDao)
        readAllData = repository.readAllData
    }

    fun addServer(server: Server){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addServer(server)
        }
    }
    fun updateServer(server: Server) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateServer(server)
        }
    }

    fun deleteServer(server: Server) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteServer(server)
        }
    }

    fun deleteAllServers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllServers()
        }
    }
}