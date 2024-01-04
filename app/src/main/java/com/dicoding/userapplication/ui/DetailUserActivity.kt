package com.dicoding.userapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.userapplication.R
import com.dicoding.userapplication.adapter.SectionAdapter
import com.dicoding.userapplication.databinding.ActivityDetailUserBinding
import com.dicoding.userapplication.repository.data.remote.response.DetailUserResponse
import com.dicoding.userapplication.viewmodel.DetailUserViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel>()


    companion object{
        const val ITEM_ID = "item_id"
        private val TAB_TITLE = intArrayOf(
            R.id.tvFollowing,
            R.id.tvFollowers
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = intent.getStringExtra(ITEM_ID)

        detailUserViewModel.setDetailUser(detailUserId = items.toString())
        supportActionBar?.hide()

        detailUserViewModel.detailUser.observe(this) { detailUser ->
            setDetailUser(detailUser)
        }

        val sectionsPagerAdapter = SectionAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager2)
        viewPager.adapter = sectionsPagerAdapter

        val tabLayoutMediator = TabLayoutMediator(binding.tabs, binding.viewPager2) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
            when (position) {
                0 -> {
                    tab.text = getString(R.string.tab_Following)
                }
                1 -> {
                    tab.text = getString(R.string.tab_Followers)
                }
                else -> {

                }
            }
        }
        tabLayoutMediator.attach()

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val actionBar = getSupportActionBar()

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

        private fun setDetailUser(detailUserResponse: DetailUserResponse) {

            binding?.apply {
                tvProfile.text = detailUserResponse.login
                tvUsername.text = detailUserResponse.name
                tvFollowing.text = resources.getString(R.string.following, detailUserResponse.following)
                tvFollowers.text = resources.getString(R.string.follower, detailUserResponse.followers)
            }

            Glide.with(binding.root.context)
            .load(detailUserResponse.avatarUrl)
            .circleCrop()
            .into(binding.ivProfile)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarDetail.visibility = if (state) View.VISIBLE else View.GONE
    }
}