package com.example.testmqtt.fragments.list

import android.content.Context
import android.content.res.TypedArray
import android.view.View
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testmqtt.R
import com.example.testmqtt.databinding.ButtonTopicBinding
import com.example.testmqtt.databinding.TopicViewBinding
import com.example.testmqtt.model.Topic
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.sql.Time
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ButtonTopicView(private val binding: ButtonTopicBinding, var context: Context): RecyclerView.ViewHolder(binding.root) {
    private var nameTxt: TextView=binding.nameTxt
    private var timeTxt: TextView=binding.timeTxt
    private var valueCb: Switch =binding.valueCb

    private lateinit var mqttClient : MqttAndroidClient
    private lateinit var clientId:String
    private lateinit var onMessage:String
    private lateinit var offMessage:String

    init {
        clientId = MqttClient.generateClientId()
    }

    public fun setUp(topic: Topic, server:String){
        nameTxt.text=topic.name
        mqttClient= MqttAndroidClient(context,server,clientId)
        parseOnOffMessage(topic.messages)
        var timeStarted:Long=0

        mqttClient?.connect()?.actionCallback= object : IMqttActionListener {

            override fun onSuccess(asyncActionToken: IMqttToken?) {
                subscribe(topic.path)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Toast.makeText(context, "failed"+exception, Toast.LENGTH_LONG).show()
            }
        }

        mqttClient?.setCallback(object: MqttCallbackExtended {

            override fun connectionLost(cause: Throwable?) {
                Toast.makeText(context, "connection lost"+cause, Toast.LENGTH_LONG).show()
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                timeTxt.text=System.currentTimeMillis().toString()
                timeStarted=System.currentTimeMillis()
                if(message.toString()==onMessage){
                    valueCb.setChecked(true)
                }
                else
                {
                    valueCb.setChecked(false)
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Toast.makeText(context, "delivery complete", Toast.LENGTH_SHORT).show()
            }

            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                Toast.makeText(context, "connectioct complete", Toast.LENGTH_SHORT).show()
            }

        })
        valueCb.setOnCheckedChangeListener{buttonView, isChecked-> if(isChecked){
            publishMessage(onMessage,topic.path)
        }
        else{
            publishMessage(offMessage,topic.path)
        }
    }
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

    private fun publishMessage(message:String,topic: String){
        try{
            val byteArray: ByteArray = message.toByteArray()
            val mqttMessage = MqttMessage()
            mqttMessage.payload = byteArray
            mqttMessage.isRetained = false
            mqttClient?.publish(topic, mqttMessage)?.actionCallback =
                object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Toast.makeText(context, "fail to publish "+exception, Toast.LENGTH_LONG).show()
                    }
                }

        } catch (e: MqttException) {
            e.printStackTrace();
        }
    }

    private fun parseOnOffMessage(message:String){
       val msgs=message.split(",").toTypedArray()
        onMessage=msgs.get(0)
        offMessage=msgs.get(1)
    }


}