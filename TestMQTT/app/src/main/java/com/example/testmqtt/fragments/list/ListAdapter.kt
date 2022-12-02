package com.example.testmqtt.fragments.list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.testmqtt.R
import com.example.testmqtt.model.Server

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var serverList = emptyList<Server>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_row,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return serverList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = serverList[position]

        holder.itemView.findViewById<TextView>(R.id.id_txt).text = currentItem.id.toString()
        holder.itemView.findViewById<TextView>(R.id.name_txt).text = currentItem.name
        holder.itemView.findViewById<TextView>(R.id.address_txt).text = currentItem.address

       holder.itemView.findViewById<ConstraintLayout>(R.id.rowLayout).setOnClickListener {

            val action = ListFragmentDirections.actionListFragmentToShowServerFragment(currentItem.id,currentItem.address)
            holder.itemView.findNavController().navigate(action)
        }
        holder.itemView.findViewById<ConstraintLayout>(R.id.rowLayout).setOnLongClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
            return@setOnLongClickListener true
        }

    }

    fun setData(server: List<Server>) {
        this.serverList = server
        notifyDataSetChanged()
    }
}