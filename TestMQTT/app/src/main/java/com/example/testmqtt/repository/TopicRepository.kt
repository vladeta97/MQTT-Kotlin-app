package com.example.testmqtt.repository

import androidx.lifecycle.LiveData
import com.example.testmqtt.data.TopicDao
import com.example.testmqtt.model.Topic

class TopicRepository(private val topicDao: TopicDao, id:Int) {
    val readAllData: LiveData<List<Topic>> = topicDao.retTopics(id)

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