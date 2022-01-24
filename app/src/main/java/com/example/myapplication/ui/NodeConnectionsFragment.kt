package com.example.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentSecondBinding
import com.example.myapplication.ui.ConnectionsListAdapter
import com.example.myapplication.data.*
import com.example.myapplication.viewmodels.ConnectionsViewModel
import com.example.myapplication.viewmodels.ConnectionsViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NodeConnectionsFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    private val args: NodeConnectionsFragmentArgs by navArgs()

    private var selectedNode: Node = Node(0, 0)

    private var availableChildren: List<NodeConnection> = emptyList()
    private var availableParents: List<NodeConnection> = emptyList()

    private var isShowingChildren = true

    private val viewModel: ConnectionsViewModel by activityViewModels {
        ConnectionsViewModelFactory(
            (activity?.application as NodeApplication).database.nodeDao()
        )
    }

    private val listAdapter = ConnectionsListAdapter { onItemClick(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedNode = Node(args.nodeId, 0)

        val recycler = binding.connectionsRecycler
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = listAdapter

        binding.showParentsButton.isEnabled = true
        binding.showChildrenButton.isEnabled = false

        binding.showChildrenButton.setOnClickListener { selectChildren() }
        binding.showParentsButton.setOnClickListener { selectParents() }

        lifecycle.coroutineScope.launch {
            val selectedNodeConnection = viewModel.getNodeConnection(args.nodeId)
            selectedNodeConnection.collect { node ->
                selectedNode = node
            }
        }

        lifecycle.coroutineScope.launch {
            val nodesListConnection = viewModel.getNodesListConnection()
            nodesListConnection.collect { nodesList: List<Node> ->
                updateChildrenList(nodesList)
                updateParentList(nodesList)
            }
        }

        selectChildren()
    }

    private fun onItemClick(connection: NodeConnection) {
        lifecycle.coroutineScope.launch {
            val isShow = viewModel.changeConnection(connection)
            if (isShow){
                Toast.makeText(activity, "Relationship removed", Toast.LENGTH_SHORT ).show();
            }}
    }

    private fun updateButtons() {
        viewModel.setChildFilter(isShowingChildren)
        binding.showParentsButton.isEnabled = isShowingChildren
        binding.showChildrenButton.isEnabled = !isShowingChildren
    }

    private fun selectChildren() {
        isShowingChildren = true
        updateButtons()
        listAdapter.submitList(availableChildren)
    }

    private fun selectParents() {
        isShowingChildren = false
        updateButtons()
        listAdapter.submitList(availableParents)
    }

    private fun extractParents(node: Node, nodesList: List<Node>): List<Node>{
        return nodesList.filter { item -> item.nodes.map { it.id }.contains(node.id) }
    }

    private fun updateChildrenList(nodes: List<Node>) {
        val actualCurrentNode = viewModel.getNode(args.nodeId)
        val restrictedValues = extractParents(actualCurrentNode, nodes).map { it.id }.toMutableList()
        restrictedValues.add(actualCurrentNode.id)
        val filteredData = nodes.filter { item -> !restrictedValues.contains(item.id) }

        val connectionsList: List<NodeConnection> = filteredData.map { item ->
            NodeConnection(
                actualCurrentNode,
                item,
                actualCurrentNode.nodes.map { it.id }.contains(item.id)
            )
        }
        availableChildren = connectionsList
        if (isShowingChildren) {
            listAdapter.submitList(availableChildren)
        }
    }

    private fun updateParentList(nodes: List<Node>) {
        val actualCurrentNode = viewModel.getNode(args.nodeId)
        val restrictedValues = actualCurrentNode.nodes.map { it.id }.toMutableList()
        restrictedValues.add(actualCurrentNode.id)
        val filteredData = nodes.filter { item -> !restrictedValues.contains(item.id) }

        val connectionsList: List<NodeConnection> = filteredData.map { item ->
            NodeConnection(
                item,
                actualCurrentNode,
                extractParents(actualCurrentNode, nodes).map { it.id }.contains(item.id)
            )
        }
        availableParents = connectionsList
        if (!isShowingChildren) {
            listAdapter.submitList(availableParents)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}