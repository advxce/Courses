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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val repo: CoursesRepository,
    private val mapper: LoadCoursesResult.Mapper<CourseState>
) : ViewModel() {

    private val _state = MutableStateFlow<CourseState>(CourseState.Loading)
    val state: StateFlow<CourseState> = _state

    fun getAllCourses(){
        viewModelScope.launch {
            _state.value = CourseState.Loading
            try {
                val allCourses = repo.getAllCourses()
                _state.value  = allCourses.map(mapper)

            }
            catch (e: Exception){
                _state.value = CourseState.Error("Network error")
            }
        }

    }

    fun updateCourse(courseUI: CourseUI) {
        viewModelScope.launch {
            try {
                Log.d("ViewModel", "Before update - UI state: ${courseUI.isBookmarked}")

                val currentCourse = repo.getCourseById(courseUI.id)
                Log.d("ViewModel", "From DB - current bookmark: ${currentCourse.isBookmarked}")

                val newBookmarkState = !currentCourse.isBookmarked
                val updatedCourse = currentCourse.copy(isBookmarked = newBookmarkState)

                Log.d("ViewModel", "Updating to: $newBookmarkState")

                repo.updateCourse(updatedCourse)

                val verifiedCourse = repo.getCourseById(courseUI.id)
                Log.d("ViewModel", "After update - verified: ${verifiedCourse.isBookmarked}")

                updateCourseInState(courseUI.id, newBookmarkState)

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

    fun getCoursesSortedByPublishDateDesc(){
        viewModelScope.launch {
            val sortedCourses = repo.getCoursesSortedByPublishDateDesc()
            _state.value = CourseState.Success(sortedCourses.first().map { it.toUI() })

        }
    }

}