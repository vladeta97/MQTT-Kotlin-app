package com.example.testmqtt.repository

import androidx.lifecycle.LiveData
import com.example.testmqtt.data.ServerDao
import com.example.testmqtt.model.Server

class ServerRepository(private val serverDao: ServerDao) {
    val readAllData: LiveData<List<Server>> = serverDao.readAllData()

    suspend fun addServer(server: Server) {
        serverDao.addServer(server)
    }

    suspend fun updateServer(server: Server) {
        serverDao.updateServer(server)
    }

    suspend fun deleteServer(server: Server) {
        serverDao.deleteServer(server)
    }

    suspend fun deleteAllServers() {
        serverDao.deleteAllServers()
    }

    suspend fun getServerandTopics(id:Int){
        serverDao.getServerWithTopics(id)
    }
}