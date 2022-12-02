package com.example.testmqtt.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.testmqtt.R
import com.example.testmqtt.databinding.ActivityMainBinding.inflate
import com.example.testmqtt.databinding.ButtonTopicBinding
import com.example.testmqtt.databinding.TopicViewBinding
import com.example.testmqtt.model.Topic

class ServerTopicsAdapter(serverAddress:String) : RecyclerView.Adapter<RecyclerView.ViewHolder,>() {

    private var topicList = emptyList<Topic>()
    private var server=serverAddress
    private lateinit var buttonBinding:ButtonTopicBinding
    private lateinit var viewTopicBinding:TopicViewBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType){
            ViewType.BUTTON.ordinal->{
                 buttonBinding=ButtonTopicBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                 ButtonTopicView(buttonBinding,parent.context)
            }
            ViewType.VALUEVIEW.ordinal->{
               viewTopicBinding=TopicViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                MyTopicView(viewTopicBinding,parent.context)
            }
            else -> {
                viewTopicBinding=TopicViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                MyTopicView(viewTopicBinding,parent.context)
            }

        }

    override fun getItemCount(): Int {
        return topicList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = topicList[position]
        if(currentItem.type==true){
            (holder as ButtonTopicView).setUp(currentItem,server)
            holder.itemView.findViewById<ConstraintLayout>(R.id.buttonView).setOnLongClickListener{
                val action = ServerTopicsFragmentDirections.actionShowFragmentToUpdateTopicFragment(currentItem,server)
                holder.itemView.findNavController().navigate(action)
                return@setOnLongClickListener true
            }
        }
        else{
            (holder as MyTopicView).setUp(currentItem,server)
            holder.itemView.findViewById<ConstraintLayout>(R.id.topicView).setOnLongClickListener{
                val action = ServerTopicsFragmentDirections.actionShowFragmentToUpdateTopicFragment(currentItem,server)
                holder.itemView.findNavController().navigate(action)
                return@setOnLongClickListener true
            }
        }



    }


    fun setData(serverTopics: List<Topic>) {
        this.topicList =serverTopics
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when(this.topicList.get(position).type){
          true -> ViewType.BUTTON.ordinal
          else-> ViewType.VALUEVIEW.ordinal
        }

       // return super.getItemViewType(position)
    }


}