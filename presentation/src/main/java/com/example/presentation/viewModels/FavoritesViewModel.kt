package com.example.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.CoursesRepository
import com.example.domain2.LoadCoursesResult
import com.example.presentation.data.CourseUI
import com.example.presentation.data.toUI
import com.example.presentation.mapper.CourseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repo: CoursesRepository,
    private val mapper: LoadCoursesResult.Mapper<CourseState>
) : ViewModel() {

    private val _state = MutableStateFlow<CourseState>(CourseState.Loading)
    val state: StateFlow<CourseState> = _state

    fun getAllFavoriteCourses(){
        viewModelScope.launch {
            val favoritesCourses = repo.getAllFavoriteCourses()
            _state.value = CourseState.Success(favoritesCourses.first().map { it.toUI() })

        }
    }

    fun updateCourses(courseUI: CourseUI) {
        viewModelScope.launch {
            try {
                Log.d("ViewModel", "Bookmark from ui: ${courseUI.isBookmarked}")

                val currentCourse = repo.getCourseById(courseUI.id)
                Log.d("ViewModel", "Bookmark From db: ${currentCourse.isBookmarked}")

                val newBookmarkState = !currentCourse.isBookmarked
                val updatedCourse = currentCourse.copy(isBookmarked = newBookmarkState)

                Log.d("ViewModel", "BookmarkState: $newBookmarkState")

                repo.updateCourse(updatedCourse)

                val verifiedCourse = repo.getCourseById(courseUI.id)
                Log.d("ViewModel", "verifiedCourses: ${verifiedCourse.isBookmarked}")
                updateCourseInState(courseUI.id, newBookmarkState)
                getAllFavoriteCourses()

            } catch (e: Exception) {
                Log.e("ViewModel", "Update error: ${e.message}", e)
                _state.value = CourseState.Error("Failed to update: ${e.message}")
            }
        }
    }

    private fun updateCourseInState(courseId: Int, newBookmarkState: Boolean) {
        val currentState = _state.value
        if (currentState is CourseState.Success) {
            val updatedCourses = currentState.courses.map { course ->
                if (course.id == courseId) {
                    course.copy(isBookmarked = newBookmarkState)
                } else {
                    course
                }
            }
            _state.value = CourseState.Success(updatedCourses)
        }
    }
}