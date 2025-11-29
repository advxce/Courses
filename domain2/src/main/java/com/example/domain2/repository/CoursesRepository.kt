package com.example.domain2.repository

import com.example.domain2.entity.Course
import com.example.domain2.entity.RemoteCourse
import com.example.domain2.mapper.LoadCoursesResult
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {

    suspend fun getAllRemoteCourses(): List<RemoteCourse>
    suspend fun getAllLocalCourses(): Flow<List<Course>>

    suspend fun getAllFavoriteCourses(): Flow<List<Course>>
    suspend fun getCoursesSortedByPublishDateDesc(): Flow<List<Course>>

    suspend fun replaceLocalCourses(courses: List<Course>)

    suspend fun updateCourse(course: Course)
    suspend fun getCourseById(id:Int): Course
}