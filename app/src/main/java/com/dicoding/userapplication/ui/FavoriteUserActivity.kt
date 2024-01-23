package com.dicoding.userapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.userapplication.R
import com.dicoding.userapplication.adapter.FavoriteUserAdapter
import com.dicoding.userapplication.databinding.ActivityFavoriteUserBinding
import com.dicoding.userapplication.repository.data.local.database.FavoriteUser
import com.dicoding.userapplication.viewmodel.FavoriteUserViewModel
import com.dicoding.userapplication.viewmodel.FavoriteUserViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite user"

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUser.layoutManager = layoutManager

        val adapter = FavoriteUserAdapter()
        adapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: FavoriteUser) {
                Intent(this@FavoriteUserActivity, DetailUserActivity::class.java)
                    .also {
                        it.putExtra(DetailUserActivity.EXTRA_ID, data.username)
                        it.putExtra(DetailUserActivity.EXTRA_URL, data.avatarUrl)
                        startActivity(it)
                    }
            }

        })

        binding.rvFavoriteUser.adapter = adapter

        val favoriteUserViewModel by viewModels<FavoriteUserViewModel> {
            FavoriteUserViewModelFactory.getInstance(this.applicationContext)
        }

        favoriteUserViewModel.getAllFavoriteUser().observe(this) { users ->
            val user = arrayListOf<FavoriteUser>()

            users.map {
                val item = FavoriteUser(username = it.username, avatarUrl = it.avatarUrl)
                user.add(item)
            }
            adapter.submitList(user)

        }

    }
}