package com.example.domain

interface CoursesRepository {

    suspend fun loadCourses(): LoadCoursesResult

}