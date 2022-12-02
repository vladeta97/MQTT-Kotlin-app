package com.example.testmqtt.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.collection.arraySetOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.testmqtt.R
import com.example.testmqtt.databinding.AddTopicBinding
import com.example.testmqtt.databinding.FragmentAddBinding
import com.example.testmqtt.fragments.list.ServerTopicsFragmentDirections
import com.example.testmqtt.fragments.update.UpdateFragmentArgs
import com.example.testmqtt.model.Server
import com.example.testmqtt.model.Topic
import com.example.testmqtt.viewModel.ServerTopicsViewModel
import com.example.testmqtt.viewModel.ServerViewModel
import com.example.testmqtt.viewModel.TopicViewModelFactory
import com.example.testmqtt.viewModel.TopicsViewModel

class AddTopic : Fragment() {

    private var _binding: AddTopicBinding? = null
    private val binding get() = _binding!!
    private lateinit var mTopicsViewModel: TopicsViewModel
    private val args by navArgs<AddTopicArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =AddTopicBinding.inflate(inflater, container, false)

        val application= requireNotNull(this.activity).application
        val srv by lazy { args.srvAddTopic }

        mTopicsViewModel= ViewModelProvider(this, TopicViewModelFactory(srv, application)).get(TopicsViewModel::class.java)

        binding.addBtn.setOnClickListener {
            insertDataToDatabase(srv)
        }
        binding.isButtonCb.setOnCheckedChangeListener{buttonView, isChecked-> if(isChecked){
            binding.onMsgEt.visibility=View.VISIBLE
            binding.offMsgEt.visibility=View.VISIBLE
        }
        else{
            binding.onMsgEt.visibility=View.INVISIBLE
            binding.offMsgEt.visibility=View.INVISIBLE
        }
        }

        return binding.root
    }

    private fun insertDataToDatabase(id:Int) {
        val name = binding.addNameEt.text.toString()
        val path = binding.addAddressEt.text.toString()
        val type = binding.isButtonCb.isChecked
        var msg=""
        var value="0"
        if(type==true){
            msg=binding.onMsgEt.text.toString()+","+binding.offMsgEt.text.toString()
            value="off"
        }


        if(inputCheck(name, path)) {

            if(args.srvAddTopic!=null){
                val topic=Topic(0,name,path,type, id,msg,value)
                mTopicsViewModel.addTopic(topic)
            }
            else{
                Toast.makeText(requireContext(), "Arg null exception", Toast.LENGTH_LONG).show()
            }


            // Add Data to database

            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate back
            val action = AddTopicDirections.actionAddTopicToServerView(id,args.srvAddTopicAddr)
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(name: String, path: String): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(path) )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}