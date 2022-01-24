package com.example.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.ui.NodesListAdapter
import com.example.myapplication.data.Node
import com.example.myapplication.data.NodeApplication
import com.example.myapplication.data.NodeListItemData
import com.example.myapplication.viewmodels.NodeListViewModel
import com.example.myapplication.viewmodels.NodeListViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class NodeListFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    private val viewModel: NodeListViewModel by activityViewModels {
        NodeListViewModelFactory(
            (activity?.application as NodeApplication).database.nodeDao()
        )
    }

    private val nodeListAdapter = NodesListAdapter { navigateToNodeId(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNodeFab.setOnClickListener { _ ->
            val action = { value: Int -> addNode(value) }

            val addNodeDialog = AddNodeDialogFragment(action)
            fragmentManager?.let { addNodeDialog.show(it, AddNodeDialogFragment.TAG) }
        }
        val recyclerView = binding.nodeListRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = nodeListAdapter
        lifecycle.coroutineScope.launch {
            val nodesFlow = viewModel.getNodesList()
            nodesFlow.collect {
                updateNodeListAdapter(it)
            }
        }
    }

    private fun updateNodeListAdapter(values: List<Node>) {
        val allChildConnections = values.map { it.nodes }.toMutableList()
        val nodesHasParents = allChildConnections.flatten().map { it.id }
        val nodes = values.map {
            NodeListItemData(
                it.id,
                it.value,
                it.nodes.isNotEmpty(),
                nodesHasParents.contains(it.id)
            )
        }
        nodeListAdapter.submitList(nodes)
    }

    private fun navigateToNodeId(NodeId: Int) {
        val action = NodeListFragmentDirections.actionFirstFragmentToSecondFragment(NodeId)
        findNavController().navigate(action)
    }

    private fun addNode(value: Int) {
        lifecycle.coroutineScope.launch {
            viewModel.addNode(value)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


