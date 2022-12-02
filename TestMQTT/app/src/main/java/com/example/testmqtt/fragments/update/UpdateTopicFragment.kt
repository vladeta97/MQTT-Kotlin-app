package com.example.testmqtt.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.testmqtt.R
import com.example.testmqtt.databinding.UpdateTopicBinding
import com.example.testmqtt.fragments.add.AddTopicDirections
import com.example.testmqtt.fragments.list.ServerTopicsFragmentDirections
import com.example.testmqtt.model.Server
import com.example.testmqtt.model.Topic
import com.example.testmqtt.viewModel.TopicViewModelFactory
import com.example.testmqtt.viewModel.TopicsViewModel

class UpdateTopicFragment : Fragment() {

    private var _binding: UpdateTopicBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateTopicFragmentArgs>()

    private lateinit var  mTopicViewModel: TopicsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UpdateTopicBinding.inflate(inflater, container, false)
        val application= requireNotNull(this.activity).application
        mTopicViewModel = ViewModelProvider(this, TopicViewModelFactory(args.currentTopic.serverId, application)).get(TopicsViewModel::class.java)

        binding.updateNameEt.setText(args.currentTopic.name)
        binding.updateAddressEt.setText(args.currentTopic.path)
        if(args.currentTopic.type==true){
            val msgs:Array<String> = parseOnOffMessage(args.currentTopic.messages)
            binding.onMsgEt.visibility=View.VISIBLE
            binding.onMsgEt.setText(msgs.get(0))
            binding.offMsgEt.visibility=View.VISIBLE
            binding.offMsgEt.setText(msgs.get(1))
        }

        binding.updateBtn.setOnClickListener {
            updateItem()
        }


        setHasOptionsMenu(true)

        return binding.root
    }

    private fun updateItem() {
        val name = binding.updateNameEt.text.toString()
        val address = binding.updateAddressEt.text.toString()

        if (inputCheck(name, address)) {
            val updatedTopic:Topic
            if(args.currentTopic.type==false){
                 updatedTopic = Topic(args.currentTopic.idTopic, name, address,args.currentTopic.type,args.currentTopic.serverId,args.currentTopic.messages,args.currentTopic.lastValue)
                 mTopicViewModel.updateTopic(updatedTopic)
            }
            else if(inputCheckButton(binding.onMsgEt.text.toString(),binding.offMsgEt.text.toString())){
                    val msg=binding.onMsgEt.text.toString()+","+ binding.offMsgEt.text.toString()
                    updatedTopic = Topic(args.currentTopic.idTopic, name, address,args.currentTopic.type,args.currentTopic.serverId,msg,args.currentTopic.lastValue)
                    mTopicViewModel.updateTopic(updatedTopic)
                }
            else{
                Toast.makeText(requireContext(), "Please fill all fields !", Toast.LENGTH_SHORT).show()
            }
            val action = UpdateTopicFragmentDirections.actionUpdateTopicFragmentToServerView(args.currentTopic.serverId,args.srvTopicAddr) // <- Pass object to Update Fragment
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Please fill all fields !", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(name: String, address: String): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(address))
    }
    private fun inputCheckButton(onMsg: String, offMsg: String): Boolean {
        return !(TextUtils.isEmpty(onMsg) && TextUtils.isEmpty(offMsg))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteTopic()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteTopic() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTopicViewModel.deleteTopic(args.currentTopic)
            Toast.makeText(
                requireContext(),
                "Successfully removed ${args.currentTopic.name}",
                Toast.LENGTH_SHORT)
                .show()
            val action = UpdateTopicFragmentDirections.actionUpdateTopicFragmentToServerView(args.currentTopic.serverId,args.srvTopicAddr)
            findNavController().navigate(action)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentTopic.name} ?")
        builder.setMessage("Are you sure to remove ${args.currentTopic.name} ?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseOnOffMessage(message:String):Array<String>{
        val msgs=message.split(",").toTypedArray()
       return msgs
    }
}
