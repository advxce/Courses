package com.example.domain2

import kotlinx.coroutines.flow.Flow

interface CoursesRepository {

    suspend fun getAllCourses(): LoadCoursesResult

    suspend fun getAllFavoriteCourses(): Flow<List<Course>>

    suspend fun updateCourse(course:Course)
    suspend fun getCourseById(id:Int):Course
}