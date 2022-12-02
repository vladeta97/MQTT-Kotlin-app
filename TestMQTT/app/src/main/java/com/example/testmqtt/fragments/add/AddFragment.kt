package com.example.testmqtt.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testmqtt.R
import com.example.testmqtt.databinding.FragmentAddBinding
import com.example.testmqtt.model.Server
import com.example.testmqtt.model.Topic
import com.example.testmqtt.viewModel.ServerViewModel

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mServerViewModel: ServerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddBinding.inflate(inflater, container, false)

        mServerViewModel = ViewModelProvider(this).get(ServerViewModel::class.java)


        binding.addBtn.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }

    private fun insertDataToDatabase() {
        val name = binding.addNameEt.text.toString()
        val address = binding.addAddressEt.text.toString()


        if(inputCheck(name, address)) {
            var empty : List<Topic> = emptyList()
            val server = Server(0, name, address)

            mServerViewModel.addServer(server)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}