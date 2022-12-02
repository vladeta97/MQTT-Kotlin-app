package com.example.testmqtt.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testmqtt.model.Server
import com.example.testmqtt.model.ServerAndTopics
import com.example.testmqtt.model.Topic


@Dao
interface ServerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addServer(server: Server)

    @Update
    suspend fun updateServer(server: Server)

    @Delete
    suspend fun deleteServer(server: Server)

    @Query("DELETE FROM server_table")
    suspend fun deleteAllServers()

    @Query("SELECT * from server_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Server>>

    @Transaction
    @Query("SELECT * FROM server_table where id=:id")
    fun getServerWithTopics(id:Int): LiveData<List<ServerAndTopics>>


   /* @Query("SELECT * from topic_table WHERE serverId=:id")
    fun retTopics(id:String):LiveData<Topic>*/


}