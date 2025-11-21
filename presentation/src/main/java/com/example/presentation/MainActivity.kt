package com.example.presentation

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.example.domain2.CourseList
import com.example.domain2.CoursesRepository
import com.example.domain2.LoadCoursesResult
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.fragments.AccountFragment
import com.example.presentation.fragments.FavoritesFragment
import com.example.presentation.fragments.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

import eightbitlab.com.blurview.BlurView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    @Inject
    lateinit var coursesRepository: CoursesRepository

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)





        initBottomBar()

//        findViewById<View>(R.id.main).post {
//            setupBlurViews()
//        }


    }

    private fun initBottomBar() {
        binding?.let { bind ->
            bind.bottomBar.apply {
                tabHome.setOnClickListener { selectTab(Tab.HOME) }
                tabFavorites.setOnClickListener { selectTab(Tab.FAVORITES) }
                tabAccount.setOnClickListener { selectTab(Tab.ACCOUNT) }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentHomeContainer, HomeFragment())
                    .addToBackStack(null)
                    .commit()

                homeCircle.background =
                    ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_active_item)


                iconHome.setColorFilter(ContextCompat.getColor(this@MainActivity,R.color.app_green))
                iconFavorites.setColorFilter(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))
                iconAccount.setColorFilter(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))

                textHome.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.app_green))
                textFavorites.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))
                textAccount.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))
            }
        }
    }

    private enum class Tab { HOME, FAVORITES, ACCOUNT }


    private fun selectTab(tab: Tab) {


        binding?.let { bind ->
            bind.bottomBar.apply {


                when (tab) {
                    Tab.HOME -> {

                        homeCircle.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_active_item)
                        favoritesCircle.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_inactive_item)
                        accountCircle.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_inactive_item)


                        iconHome.setColorFilter(ContextCompat.getColor(this@MainActivity,R.color.app_green))

                        iconFavorites.setColorFilter(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))
                        iconAccount.setColorFilter(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))

                        textHome.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.app_green))
                        textFavorites.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))
                        textAccount.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentHomeContainer, HomeFragment())
                            .addToBackStack(null)
                            .commit()

                    }

                    Tab.FAVORITES -> {
                        homeCircle.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_inactive_item)
                        favoritesCircle.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_active_item)
                        accountCircle.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_inactive_item)


                        iconFavorites.setColorFilter(ContextCompat.getColor(this@MainActivity,R.color.app_green))
                        iconHome.setColorFilter(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))
                        iconAccount.setColorFilter(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))

                        textFavorites.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.app_green))
                        textHome.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))
                        textAccount.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentHomeContainer, FavoritesFragment())
                            .addToBackStack(null)
                            .commit()
                    }

                    Tab.ACCOUNT -> {
                        homeCircle.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_inactive_item)
                        accountCircle.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_active_item)
                        favoritesCircle.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_inactive_item)


                        iconAccount.setColorFilter(ContextCompat.getColor(this@MainActivity,R.color.app_green))
                        iconFavorites.setColorFilter(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))
                        iconHome.setColorFilter(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))

                        textAccount.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.app_green))
                        textHome.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))
                        textFavorites.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.app_textview_color))

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentHomeContainer, AccountFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        }
    }

    private fun setupBlurViews() {
        val blurViewRating = findViewById<BlurView?>(R.id.blurViewRating)
        val blurViewBookmark = findViewById<BlurView?>(R.id.blurViewBookmark)
        val blurViewContent = findViewById<BlurView?>(R.id.blurViewContent)

        if (blurViewRating == null && blurViewBookmark == null && blurViewContent == null) {
            Log.e("BlurDebug", "No BlurViews found")
            return
        }

        val rootView = findViewById<ViewGroup>(R.id.main)

        val windowBackground = rootView.background

        fun BlurView.initBlur() {
            setupWith(rootView)

                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(18f)
                .setBlurAutoUpdate(true)
        }

        blurViewRating?.initBlur()
        blurViewBookmark?.initBlur()
        blurViewContent?.initBlur()
    }




    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}