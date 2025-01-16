package com.app.interestingstuff.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.interestingstuff.databinding.ItemInterestingBinding
import com.app.interestingstuff.model.InterestingItem
import com.bumptech.glide.Glide

// Extending ListAdapter which handles list differences automatically
class InterestingItemAdapter(
    private val onItemClick: (InterestingItem) -> Unit
) : ListAdapter<InterestingItem, InterestingItemAdapter.ViewHolder>(ItemDiffCallback()) {

    // ViewHolder holds references to the views in our item layout
    class ViewHolder(
        private val binding: ItemInterestingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: InterestingItem, onItemClick: (InterestingItem) -> Unit) {
            // Set the item's data to the views
            binding.apply {
                itemTitle.text = item.title
                itemDescription.text = item.description
                itemRating.rating = item.rating

                // Load image if available
                item.imageUri?.let { uri ->
                    Glide.with(itemImage)
                        .load(uri)
                        .centerCrop()
                        .into(itemImage)
                }

                // Set click listener
                root.setOnClickListener { onItemClick(item) }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemInterestingBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding)
            }
        }
    }

    // Called when RecyclerView needs a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    // Called to display data at a specific position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}

// DiffUtil helps RecyclerView calculate which items have changed in the list
private class ItemDiffCallback : DiffUtil.ItemCallback<InterestingItem>() {
    override fun areItemsTheSame(oldItem: InterestingItem, newItem: InterestingItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: InterestingItem, newItem: InterestingItem): Boolean {
        return oldItem == newItem
    }
}