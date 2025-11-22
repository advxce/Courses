package com.example.presentation


import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.fragments.AccountFragment
import com.example.presentation.fragments.FavoritesFragment
import com.example.presentation.fragments.HomeFragment
import com.example.presentation.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val mainViewModel: MainViewModel by viewModels()

    enum class Tab { HOME, FAVORITES, ACCOUNT }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupBottomBar()

        mainViewModel.selectedTab.observe(this) { tab ->
            updateTabUI(tab)
            openFragment(
                when (tab) {
                    Tab.HOME -> HomeFragment()
                    Tab.FAVORITES -> FavoritesFragment()
                    Tab.ACCOUNT -> AccountFragment()
                }
            )
        }
    }

    private fun setupBottomBar() = binding?.bottomBar?.apply {
        tabHome.setOnClickListener { mainViewModel.selectTab(Tab.HOME) }
        tabFavorites.setOnClickListener { mainViewModel.selectTab(Tab.FAVORITES) }
        tabAccount.setOnClickListener { mainViewModel.selectTab(Tab.ACCOUNT) }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHomeContainer, fragment)
            .commit()
    }

    private fun updateTabUI(tab: Tab) = binding?.bottomBar?.apply {

        fun activate(circle: LinearLayout, icon: ImageView, text: TextView) {
            circle.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_active_item)
            icon.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.app_green))
            text.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.app_green))
        }

        fun deactivate(circle: LinearLayout, icon: ImageView, text: TextView) {
            circle.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_inactive_item)
            icon.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.app_textview_color))
            text.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.app_textview_color))
        }

        deactivate(homeCircle, iconHome, textHome)
        deactivate(favoritesCircle, iconFavorites, textFavorites)
        deactivate(accountCircle, iconAccount, textAccount)

        when (tab) {
            Tab.HOME -> activate(homeCircle, iconHome, textHome)
            Tab.FAVORITES -> activate(favoritesCircle, iconFavorites, textFavorites)
            Tab.ACCOUNT -> activate(accountCircle, iconAccount, textAccount)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}