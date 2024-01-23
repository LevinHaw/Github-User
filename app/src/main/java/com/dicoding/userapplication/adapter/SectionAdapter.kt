package com.dicoding.userapplication.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.userapplication.ui.DetailUserActivity
import com.dicoding.userapplication.ui.TabFragment

class SectionAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    private var username: String? = activity.intent.getStringExtra(DetailUserActivity.EXTRA_ID)

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = TabFragment()
        fragment.arguments = Bundle().apply {
            putInt(TabFragment.ARG_POSITION, position)
            putString(TabFragment.ARG_USERNAME, username)
        }
        return fragment
    }
}