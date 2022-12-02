package com.example.testmqtt.fragments.list

import android.app.AlertDialog
import android.app.Application
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testmqtt.R
import com.example.testmqtt.databinding.ServerViewBinding
import com.example.testmqtt.model.Server
import com.example.testmqtt.viewModel.TopicViewModelFactory
import com.example.testmqtt.viewModel.TopicsViewModel


class ServerTopicsFragment : Fragment() {

   private var _binding: ServerViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var mTopicsViewModel: TopicsViewModel
    private val args by navArgs<ServerTopicsFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ServerViewBinding.inflate(inflater, container, false)

        val adapter = ServerTopicsAdapter(args.srvViewAddress)
        val recyclerView = binding.recyclerview

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)

        val application= requireNotNull(this.activity).application

        val srv:Int by lazy { args.srvViewID  }
        mTopicsViewModel= ViewModelProvider(this,TopicViewModelFactory(srv, application)).get(TopicsViewModel::class.java)
        mTopicsViewModel.readAllData.observe(viewLifecycleOwner,Observer{topics->adapter.setData(topics) })


        binding.floatingActionButton.setOnClickListener {
            val action = ServerTopicsFragmentDirections.actionShowFragmentToAddTopicFragment(srv,args.srvViewAddress) // <- Pass object to Update Fragment
            findNavController().navigate(action)
        }

        // Add menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllServers()
        }
        return super.onOptionsItemSelected(item)
    }

    // Implement logic to delete all users
    private fun deleteAllServers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTopicsViewModel.deleteAllTopics(args.srvViewID)
            Toast.makeText(
                requireContext(),
                "Successfully removed everything",
                Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything ?")
        builder.setMessage("Are you sure to remove everything ?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}