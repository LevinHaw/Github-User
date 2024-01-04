package com.dicoding.userapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.userapplication.adapter.UserAdapter
import com.dicoding.userapplication.databinding.ActivityMainBinding
import com.dicoding.userapplication.repository.data.remote.response.ItemsItem
import com.dicoding.userapplication.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvListUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListUser.addItemDecoration(itemDecoration)

        mainViewModel.getIsLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.getListUser.observe(this) { items ->
            setListUser(items)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    val query = searchView.text.toString()
                    mainViewModel.searchUser(query)
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
        }
    }

    private fun setListUser(items: List<ItemsItem>){
        val adapter = UserAdapter()
        adapter.submitList(items)
        binding.rvListUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}