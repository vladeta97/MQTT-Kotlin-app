package com.example.testmqtt.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.testmqtt.R
import com.example.testmqtt.databinding.FragmentUpdateBinding
import com.example.testmqtt.model.Server
import com.example.testmqtt.model.Topic
import com.example.testmqtt.viewModel.ServerViewModel

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var  mServerViewModel: ServerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        mServerViewModel = ViewModelProvider(this).get(ServerViewModel::class.java)

        binding.updateNameEt.setText(args.currentServer.name)
        binding.updateAddressEt.setText(args.currentServer.address)


        binding.updateBtn.setOnClickListener {
            updateItem()
        }

        // Add menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun updateItem() {
        val name = binding.updateNameEt.text.toString()
        val address = binding.updateAddressEt.text.toString()

        if (inputCheck(name, address)) {

            var empty : List<Topic> = emptyList()
            val updatedServer = Server(args.currentServer.id, name, address)

            mServerViewModel.updateServer(updatedServer)
            Toast.makeText(requireContext(), "Updated Successfully !", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all fields !", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(name: String, address: String): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(address))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteServer()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteServer() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mServerViewModel.deleteServer(args.currentServer)
            Toast.makeText(
                requireContext(),
                "Successfully removed ${args.currentServer.name}",
                Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentServer.name} ?")
        builder.setMessage("Are you sure to remove ${args.currentServer.name} ?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}