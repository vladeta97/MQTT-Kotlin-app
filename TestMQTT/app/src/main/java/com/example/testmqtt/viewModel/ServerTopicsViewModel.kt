package com.example.testmqtt.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.testmqtt.data.ServerDatabase
import com.example.testmqtt.model.Server
import com.example.testmqtt.model.ServerAndTopics
import com.example.testmqtt.model.Topic
import com.example.testmqtt.repository.TopicsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServerTopicsViewModel (application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<ServerAndTopics>>
    private val topicsRepository:TopicsRepository

    init{
        val topicsDao=ServerDatabase.getDatabase(application).topicDao()
        topicsRepository=TopicsRepository(topicsDao)
        readAllData=topicsRepository.readAllData
    }

    fun addTopic(topic: Topic){
        viewModelScope.launch(Dispatchers.IO) {
            topicsRepository.addTopic(topic)
        }
    }
    fun updateTopic(topic: Topic) {
        viewModelScope.launch(Dispatchers.IO) {
            topicsRepository.updateTopic(topic)
        }
    }

    fun deleteTopic(topic: Topic) {
        viewModelScope.launch(Dispatchers.IO) {
            topicsRepository.deleteTopic(topic)
        }
    }

    fun deleteAllTopics(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            topicsRepository.deleteAllTopics(id)
        }
    }

}