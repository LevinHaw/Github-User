package com.dicoding.userapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.userapplication.databinding.ItemListUserBinding
import com.dicoding.userapplication.repository.data.local.database.FavoriteUser

class FavoriteUserAdapter: ListAdapter<FavoriteUser, FavoriteUserAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(user)
        }

    }

    class FavoriteViewHolder(private val binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavoriteUser){
            binding.tvUsername.text = user.username
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.civAvatar)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        Companion.onItemClickCallback = onItemClickCallback
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>(){
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
        }

        private lateinit var onItemClickCallback: OnItemClickCallback
    }

}