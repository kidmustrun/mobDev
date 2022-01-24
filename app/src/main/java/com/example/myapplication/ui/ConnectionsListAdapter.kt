package com.example.myapplication.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ConnectionsListItemBinding
import com.example.myapplication.data.NodeConnection


class ConnectionsListAdapter(
    private val onItemClicked: (NodeConnection) -> Unit
) : ListAdapter<NodeConnection, ConnectionsListAdapter.ItemViewHolder>(DiffCallback) {
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<NodeConnection>() {
            override fun areItemsTheSame(
                oldItem: NodeConnection,
                newItem: NodeConnection
            ): Boolean {
                return oldItem.from.id == newItem.from.id && oldItem.to.id == newItem.to.id
            }

            override fun areContentsTheSame(
                oldItem: NodeConnection,
                newItem: NodeConnection
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewHolder = ItemViewHolder(
            ConnectionsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewHolder(
        private var binding: ConnectionsListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(connection: NodeConnection) {
            binding.connectionsListItem.text =
                "id: ${connection.from.id} | value: ${connection.from.value} --- id: ${connection.to.id} | value: ${connection.to.value}"
            if (connection.isActive) {
                binding.root.setBackgroundColor(Color.GREEN)
            } else {
                binding.root.setBackgroundColor(Color.WHITE)
            }
        }
    }
}
