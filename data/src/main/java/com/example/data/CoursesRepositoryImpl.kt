package com.example.data

import com.example.domain.CoursesRepository
import com.example.domain.LoadCoursesResult
import java.lang.Exception
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(

    private val service: CourseRemoteService
) : CoursesRepository {
    override suspend fun loadCourses(): LoadCoursesResult {
        return try {
            val courses: CourseListRemoteData = service.loadCourses()
            LoadCoursesResult.Success(courses.toDomain())
        } catch (e: Exception) {
            LoadCoursesResult.Error(e.message ?: "error")
        }
    }
}