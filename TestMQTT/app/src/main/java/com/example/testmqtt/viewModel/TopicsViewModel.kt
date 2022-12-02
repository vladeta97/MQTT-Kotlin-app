package com.example.testmqtt.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.testmqtt.data.ServerDatabase
import com.example.testmqtt.model.Server
import com.example.testmqtt.model.ServerAndTopics
import com.example.testmqtt.model.Topic
import com.example.testmqtt.repository.ServerRepository
import com.example.testmqtt.repository.TopicRepository
import com.example.testmqtt.repository.TopicsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopicsViewModel(private val id:Int, application: Application):AndroidViewModel(application) {

    private val repository: TopicRepository
    val readAllData : LiveData<List<Topic>>
    init {
        val topicDao = ServerDatabase.getDatabase(application).topicDao()
        repository= TopicRepository(topicDao,id)
        readAllData = repository.readAllData
    }
    fun addTopic(topic: Topic){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTopic(topic)
        }
    }
    fun updateTopic(topic: Topic) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTopic(topic)
        }
    }

    fun deleteTopic(topic: Topic) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTopic(topic)
        }
    }

    fun deleteAllTopics(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTopics(id)
        }
    }
}