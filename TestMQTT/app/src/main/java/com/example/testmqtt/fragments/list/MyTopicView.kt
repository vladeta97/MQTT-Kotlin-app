package com.example.testmqtt.fragments.list

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.testmqtt.R
import com.example.testmqtt.databinding.TopicViewBinding
import org.eclipse.paho.client.mqttv3.*

import com.example.testmqtt.model.Topic
import org.eclipse.paho.android.service.MqttAndroidClient

class MyTopicView(private val binding:TopicViewBinding, var context:Context): RecyclerView.ViewHolder(binding.root) {

    private var nameTxt:TextView=binding.nameTxt
    private var valueTxt:TextView=binding.valueTxt
    private lateinit var mqttClient : MqttAndroidClient
    private lateinit var clientId:String

    init {
        clientId = MqttClient.generateClientId()

    }

    public fun setUp(topic: Topic,server:String){
        nameTxt.text=topic.name
        mqttClient= MqttAndroidClient(context,server,clientId)

        mqttClient?.connect()?.actionCallback= object : IMqttActionListener{
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                subscribe(topic.path)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Toast.makeText(context, "failed"+exception, Toast.LENGTH_LONG).show()
            }
        }

        mqttClient?.setCallback(object:MqttCallbackExtended{
            override fun connectionLost(cause: Throwable?) {
                Toast.makeText(context, "connection lost"+cause, Toast.LENGTH_LONG).show()
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                valueTxt.text=message.toString()
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }

            override fun connectComplete(reconnect: Boolean, serverURI: String?) {

            }

        })


    }

    private fun subscribe(topic:String){
        mqttClient.subscribe(topic,1)?.actionCallback= object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Toast.makeText(context, "failed topic"+exception, Toast.LENGTH_LONG).show()
            }
        }
    }


}
