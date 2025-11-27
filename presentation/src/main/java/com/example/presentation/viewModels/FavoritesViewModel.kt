package com.example.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.repository.CoursesRepository
import com.example.domain2.mapper.LoadCoursesResult
import com.example.domain2.useCase.GetAllFavoritesCoursesUseCase
import com.example.domain2.useCase.ToggleCourseBookmarkUseCase
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
    private val getAllFavoritesCoursesUseCase: GetAllFavoritesCoursesUseCase,
    private val toggleCourseBookmarkUseCase: ToggleCourseBookmarkUseCase,
    private val repo: CoursesRepository,
    private val mapper: LoadCoursesResult.Mapper<CourseState>
) : ViewModel() {

    private val _state = MutableStateFlow<CourseState>(CourseState.Loading)
    val state: StateFlow<CourseState> = _state



    fun getAllFavoriteCourses(){
        viewModelScope.launch {
            val favoritesCourses = getAllFavoritesCoursesUseCase.invoke()
            _state.value = CourseState.Success(favoritesCourses.first().map { it.toUI() })

        }
    }

    fun updateCourse(course: CourseUI){
        viewModelScope.launch {
            try {
                val updatedCourse = toggleCourseBookmarkUseCase.invoke(course.id)
                updateCourseInState(updatedCourse.id, updatedCourse.isBookmarked)
                getAllFavoriteCourses()
            }
            catch (e: Exception){
                _state.value = CourseState.Error("Failed to update")
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