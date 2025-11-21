package com.example.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.CoursesRepository
import com.example.domain.LoadCoursesResult
import com.example.presentation.data.CourseUI
import com.example.presentation.mapper.CourseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val mapper: LoadCoursesResult.Mapper<CourseState>
): ViewModel() {

    private val _coursesState = MutableStateFlow<CourseState>(CourseState.Loading)
    val coursesState : StateFlow<CourseState> = _coursesState.asStateFlow()

    fun loadCourses(){
        viewModelScope.launch {

            _coursesState.value = CourseState.Loading

            try {
                val courses = coursesRepository.loadCourses()

                _coursesState.value = courses.map(mapper)
            }
            catch (e: Exception) {
                _coursesState.value = CourseState.Error("Ошибка сети: ${e.message}")
            }


        }
    }

    fun onBookmarkClick(course: CourseUI) {
        viewModelScope.launch {
            coursesRepository.toggleBookmark(course.id, course.isBookmarked)

            val currentState = _coursesState.value
            if (currentState is CourseState.Success) {
                val updatedList = currentState.courses.map { c ->
                    if (c.id == course.id) c.copy(isBookmarked = !c.isBookmarked)
                    else c
                }
                _coursesState.value = CourseState.Success(updatedList)
            }
        }
    }
}