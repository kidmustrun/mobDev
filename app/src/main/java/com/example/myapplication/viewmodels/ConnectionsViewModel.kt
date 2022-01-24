package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.Node
import com.example.myapplication.data.NodeConnection
import com.example.myapplication.data.Dao
import kotlinx.coroutines.flow.Flow


class ConnectionsViewModel(private val nodeDao: Dao) : ViewModel() {
    private var isFilterOnChildren = true
    fun getNodesListConnection(): Flow<List<Node>> = nodeDao.getAllNodesConnection()

    fun getNodeConnection(id: Int): Flow<Node> = nodeDao.getNodeByIdConnection(id)

    fun getNode(id: Int): Node = nodeDao.getNodeById(id)

    private fun addRelation(parent: Node, child: Node) {
        val updatedParentNodes = parent.nodes.toMutableList()
        updatedParentNodes.add(child)
        val newParent = Node(parent.id, parent.value, updatedParentNodes)
        nodeDao.updateNode(newParent)
    }

    private fun removeRelation(parent: Node, child: Node) {
        val updatedParentNodes = parent.nodes.toMutableList().filter { it.id != child.id }
        val newParent = Node(parent.id, parent.value, updatedParentNodes)
        nodeDao.updateNode(newParent)
    }

    fun changeConnection(connection: NodeConnection): Boolean {
        val actualFrom = nodeDao.getNodeById(connection.from.id)
        val actualTo = nodeDao.getNodeById(connection.to.id)
        var result: Boolean
        if (actualFrom.nodes.map { it.id }.contains(actualTo.id)) {
            removeRelation(actualFrom, actualTo,)
            result = true
        } else {
            addRelation(actualFrom, actualTo)
            result = false
        }
        return result
    }

    fun setChildFilter(isChildren: Boolean) {
        isFilterOnChildren = isChildren
    }
}

class ConnectionsViewModelFactory(
    private val nodeDao: Dao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConnectionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConnectionsViewModel(nodeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}