package com.example.testmqtt.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testmqtt.model.Server
import com.example.testmqtt.model.Topic
//import com.example.testmqtt.model.TopicTypeConverter

@Database(
    entities = [Server::class, Topic::class],
    version = 1,                // <- Database version
    exportSchema = true
)
//@TypeConverters(TopicTypeConverter::class)

abstract class ServerDatabase:RoomDatabase() {
    abstract fun serverDao():ServerDao
    abstract fun topicDao():TopicDao

    companion object{
        @Volatile
        private var INSTANCE : ServerDatabase?=null

        fun getDatabase(context: Context): ServerDatabase{
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ServerDatabase::class.java,
                    "server_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}