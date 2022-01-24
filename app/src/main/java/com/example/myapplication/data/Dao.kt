package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM Node ORDER BY id ASC")
    fun getAllNodesConnection(): Flow<List<Node>>

    @Insert
    fun pushNodes(vararg nodes: Node)

    @Update
    fun updateNode(node: Node)

    @Query("SELECT * FROM Node WHERE id = :nodeId")
    fun getNodeByIdConnection(nodeId: Int): Flow<Node>

    @Query("SELECT * FROM Node WHERE id = :nodeId")
    fun getNodeById(nodeId: Int): Node


    @Query("SELECT COUNT(id) FROM Node")
    fun getSize(): Int
}