package com.example.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import com.example.domain.CourseList
import com.example.domain.CoursesRepository
import com.example.domain.LoadCoursesResult
import dagger.hilt.android.AndroidEntryPoint

import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var coursesRepository: CoursesRepository

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        load()


        findViewById<View>(R.id.main).post {
            setupBlurViews()
        }

//
//        val blurViewRating = findViewById<BlurView>(R.id.blurViewRating)
//        val blurViewBookmark = findViewById<BlurView>(R.id.blurViewBookmark)
//        val blurViewContent = findViewById<BlurView>(R.id.blurViewContent)
//        val decorView = window.decorView
//        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
//        val windowBackground = decorView.background
//
//                blurViewContent.setupWith(rootView)
//                    .setFrameClearDrawable(windowBackground)
//                    .setBlurRadius(15f)
//
//                blurViewRating.setupWith(rootView)
//                    .setFrameClearDrawable(windowBackground)
//                    .setBlurRadius(15f)
//
//                blurViewBookmark.setupWith(rootView)
//                    .setFrameClearDrawable(windowBackground)
//                    .setBlurRadius(15f)

//        val blurView = findViewById<BlurView>(R.id.blurView)
//        val decorView = window.decorView
//        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
//        val windowBackground = decorView.background
//
//        blurView.setupWith(rootView)
//            .setFrameClearDrawable(windowBackground)
//            .setBlurRadius(15f)


    }

    private fun setupBlurViews() {
        val blurViewRating = findViewById<BlurView?>(R.id.blurViewRating)
        val blurViewBookmark = findViewById<BlurView?>(R.id.blurViewBookmark)
        val blurViewContent = findViewById<BlurView?>(R.id.blurViewContent)

        if (blurViewRating == null && blurViewBookmark == null && blurViewContent == null) {
            Log.e("BlurDebug", "No BlurViews found")
            return
        }

        // Корневой layout экрана
        val rootView = findViewById<ViewGroup>(R.id.main)

        // Фон (у тебя он чёрный, но размывать будем картинку внутри CardView)
        val windowBackground = rootView.background

        fun BlurView.initBlur() {
            setupWith(rootView)

                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(18f)          // для наглядности можно побольше
                .setBlurAutoUpdate(true)
        }

        blurViewRating?.initBlur()
        blurViewBookmark?.initBlur()
        blurViewContent?.initBlur()
    }

    fun load() {
        coroutineScope.launch {
            try {
                val result = coursesRepository.loadCourses()
                result.map(object : LoadCoursesResult.Mapper<Unit> {
                    override fun mapSuccess(courses: CourseList) {

                        Log.i("test", "${courses.listCourses}")
                    }

                    override fun mapError(message: String) {
                        Log.i("test", "${message}")
                    }

                })
            } catch (e: Exception) {
                Log.i("test", "${e.message}")
            }
        }
    }
}