package com.example.domain2.useCase

import com.example.domain2.entity.Course
import com.example.domain2.repository.CoursesRepository
import javax.inject.Inject

interface ToggleCourseBookmarkUseCase {
    suspend fun invoke(courseId: Int): Course
}

class ToggleCourseBookmarkUseCaseImpl @Inject constructor(
    private val repository: CoursesRepository
) : ToggleCourseBookmarkUseCase {
    override suspend fun invoke(courseId: Int): Course {
        val currentCourse = repository.getCourseById(courseId)
        val updatedCourse = currentCourse.copy(isBookmarked = !currentCourse.isBookmarked)

        repository.updateCourse(updatedCourse)
        return updatedCourse
    }
}