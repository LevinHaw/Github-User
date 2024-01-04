package com.dicoding.userapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.userapplication.R
import com.dicoding.userapplication.adapter.UserAdapter
import com.dicoding.userapplication.databinding.FragmentTabBinding
import com.dicoding.userapplication.viewmodel.FollowerViewModel
import com.dicoding.userapplication.viewmodel.FollowingViewModel

class TabFragment : Fragment() {

    private lateinit var followersViewModel: FollowerViewModel
    private lateinit var followingViewModel: FollowingViewModel

    private lateinit var binding: FragmentTabBinding
    private lateinit var adapter: UserAdapter

    private var position: Int = 0
    private var username: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME, "")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followingViewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
        followersViewModel = ViewModelProvider(this).get(FollowerViewModel::class.java)

        setupRecyclerView()
        observeFollowersOrFollowing()
    }


    private fun setupRecyclerView() {
        adapter = UserAdapter()
        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())

        val itemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvFollow.addItemDecoration(itemDecoration)
    }

    private fun observeFollowersOrFollowing() {
        if (position == 0) {
            followingViewModel.setUserLogin(username)
            followingViewModel.following.observe(viewLifecycleOwner) { following ->
                Log.d("Logku", "ini di following")
                showLoading(false)
                adapter.submitList(following)
                binding.rvFollow.adapter = adapter

            }
        } else if (position == 1) {
            followersViewModel.setUserLogin(username)
            followersViewModel.followers.observe(viewLifecycleOwner) { followers ->
                Log.d("Logku", "ini di followers")
                showLoading(false)
                adapter.submitList(followers)
                binding.rvFollow.adapter = adapter

            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFollow.visibility = View.VISIBLE
            binding.rvFollow.visibility = View.GONE
        } else {
            binding.progressBarFollow.visibility = View.GONE
            binding.rvFollow.visibility = View.VISIBLE
        }
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"

        fun newInstance(position: Int, username: String): TabFragment {
            val fragment = TabFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            args.putString(ARG_USERNAME, username)
            fragment.arguments = args
            return fragment
        }
    }
}