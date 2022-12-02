package com.example.testmqtt.repository

import androidx.lifecycle.LiveData
import com.example.testmqtt.data.ServerDao
import com.example.testmqtt.data.TopicDao
import com.example.testmqtt.model.Server
import com.example.testmqtt.model.ServerAndTopics
import com.example.testmqtt.model.Topic

class TopicsRepository(private val topicDao: TopicDao) {
    val readAllData: LiveData<List<ServerAndTopics>> = topicDao.retTopicsSrv()

    suspend fun addTopic(topic: Topic) {
        topicDao.addTopic(topic)
    }

    suspend fun updateTopic(topic: Topic) {
        topicDao.updateTopic(topic)
    }

    suspend fun deleteTopic(topic: Topic) {
        topicDao.deleteTopic(topic)
    }

    suspend fun deleteAllTopics(id:Int) {
        topicDao.deleteAllTopics(id)
    }
}