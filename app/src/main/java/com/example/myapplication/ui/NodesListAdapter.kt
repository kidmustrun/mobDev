package com.example.myapplication.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.NodesListItemBinding
import com.example.myapplication.data.NodeListItemData


class NodesListAdapter(
    private val onItemClicked: (Int) -> Unit
) : ListAdapter<NodeListItemData, NodesListAdapter.ItemViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<NodeListItemData>() {
            override fun areItemsTheSame(
                oldItem: NodeListItemData,
                newItem: NodeListItemData
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: NodeListItemData,
                newItem: NodeListItemData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewHolder = ItemViewHolder(
            NodesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewHolder(
        private var binding: NodesListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(node: NodeListItemData) {
            binding.nodesListItem.text = "id: ${node.id} | value: ${node.value}"

            if (node.hasParents && node.hasChildren) {
                binding.root.setBackgroundColor(Color.RED)
            } else if (node.hasParents) {
                binding.root.setBackgroundColor(Color.BLUE)
            } else if (node.hasChildren) {
                binding.root.setBackgroundColor(Color.YELLOW)
            } else {
                binding.root.setBackgroundColor(Color.WHITE)
            }

        }
    }
}
