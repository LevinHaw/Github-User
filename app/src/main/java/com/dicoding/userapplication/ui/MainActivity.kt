package com.dicoding.userapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.userapplication.R
import com.dicoding.userapplication.adapter.UserAdapter
import com.dicoding.userapplication.databinding.ActivityMainBinding
import com.dicoding.userapplication.repository.data.remote.response.ItemsItem
import com.dicoding.userapplication.ui.setting.SettingActivity
import com.dicoding.userapplication.ui.setting.SettingPreference
import com.dicoding.userapplication.viewmodel.SettingViewModelFactory
import com.dicoding.userapplication.ui.setting.dataStore
import com.dicoding.userapplication.viewmodel.MainViewModel
import com.dicoding.userapplication.viewmodel.SettingViewModel

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

        mainViewModel.getIsLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.getListUser.observe(this) { items ->
            setListUser(items)
        }

        // Inisiasi untuk setting dark mode
        val pref = SettingPreference.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]

        // Perubahan tema (Light/Dark Mode)
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.searchBar.inflateMenu(R.menu.option_menu)
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 ->{
                    startActivity(Intent(this, FavoriteUserActivity::class.java))
                    true
                }

                R.id.menu2 -> {
                    startActivity(Intent(this, SettingActivity::class.java))
                    true
                }

                else -> false
            }
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