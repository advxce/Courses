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
class FavoritesViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val mapper: LoadCoursesResult.Mapper<CourseState>
):ViewModel() {
    private val _coursesState = MutableStateFlow<CourseState>(CourseState.Loading)
    val coursesState: StateFlow<CourseState> = _coursesState.asStateFlow()

    fun loadCourses() {
        viewModelScope.launch {
            _coursesState.value = CourseState.Loading
            try {
                val result = coursesRepository.getBookmarkedCourses()
                _coursesState.value = result.map(mapper)
            } catch (e: Exception) {
                _coursesState.value = CourseState.Error("Ошибка: ${e.message}")
            }
        }
    }

    fun onBookmarkClick(course: CourseUI) {
        viewModelScope.launch {
            coursesRepository.toggleBookmark(course.id, course.isBookmarked)

            loadCourses()
        }
    }
}