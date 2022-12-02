package com.example.testmqtt.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testmqtt.model.ServerAndTopics
import com.example.testmqtt.model.Topic
@Dao
interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTopic(topic: Topic)

    @Update
    suspend fun updateTopic(topic: Topic)

    @Delete
    suspend fun deleteTopic(topic: Topic)

    @Query("DELETE FROM topic_table where serverId=:id")
    suspend fun deleteAllTopics(id:Int)

    @Query("SELECT * from topic_table WHERE serverId=:id")
    fun retTopics(id:Int):LiveData<List<Topic>>

    @Transaction
    @Query("SELECT * FROM server_table, topic_table WHERE id=serverid")
    fun retTopicsSrv():LiveData<List<ServerAndTopics>>

}