package com.example.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.repository.CoursesRepository
import com.example.domain2.mapper.LoadCoursesResult
import com.example.domain2.useCase.GetAllCoursesUseCase
import com.example.domain2.useCase.GetCoursesBySortPublishDateUseCase
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
class CourseViewModel @Inject constructor(
    private val getAllCoursesUseCase: GetAllCoursesUseCase,
    private val toggleCourseBookmarkUseCase: ToggleCourseBookmarkUseCase,
    private val mapper: LoadCoursesResult.Mapper<CourseState>,
    private val getCoursesBySortPublishDateUseCase: GetCoursesBySortPublishDateUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<CourseState>(CourseState.Loading)
    val state: StateFlow<CourseState> = _state


    fun getAllCourses() {
        viewModelScope.launch {
            _state.value = CourseState.Loading
            try {
                val allCourses = getAllCoursesUseCase.invoke()
                _state.value = allCourses.map(mapper)
            } catch (e: Exception) {
                _state.value = CourseState.Error("Network error")
            }
        }
    }

    fun updateCourse(course: CourseUI){
        viewModelScope.launch {
            try {
                val updatedCourse = toggleCourseBookmarkUseCase.invoke(course.id)
                updateCourseInState(updatedCourse.id, updatedCourse.isBookmarked)
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

    fun getCourseBySortDate(){
        viewModelScope.launch {
            val getCourses = getCoursesBySortPublishDateUseCase.getCoursesSortedByPublishDateDesc()
            val courseList = getCourses.first().map { it.toUI() }
            _state.value = CourseState.Success(courseList)
        }
    }

}