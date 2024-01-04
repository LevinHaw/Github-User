package com.dicoding.userapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.userapplication.databinding.ItemListUserBinding
import com.dicoding.userapplication.repository.data.remote.response.ItemsItem

import com.dicoding.userapplication.ui.DetailUserActivity

class UserAdapter: ListAdapter<ItemsItem, UserAdapter.ListViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class ListViewHolder(private val binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: ItemsItem){
            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.ITEM_ID, user.login)
                itemView.context.startActivity(intent)
            }
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.civAvatar)
            binding.tvUsername.text = user.login
            binding.tvAccount.text = user.id.toString()
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: ItemsItem)
    }
}