package com.example.testmqtt.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Entity(tableName = "server_table")
data class Server(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val address: String,
     //val topics: List<Topic>
    ): Parcelable

/*@Parcelize
data class Topic(val name:String, val path:String, val type:Int):Parcelable

class TopicTypeConverter{
    @TypeConverter
    fun listToJson(value: List<Topic>?)= Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String)=Gson().fromJson(value,Array<Topic>::class.java).toList()
}*/