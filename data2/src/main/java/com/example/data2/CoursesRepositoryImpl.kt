package com.example.data2

import android.util.Log
import com.example.data2.database.entity.CourseEntity
import com.example.data2.database.DAO
import com.example.data2.database.entity.toDomain
import com.example.data2.database.entity.toEntity
import com.example.data2.retrofit.service.CourseRemoteService
import com.example.data2.retrofit.NetworkChecker
import com.example.domain2.entity.Course
import com.example.domain2.entity.RemoteCourse
import com.example.domain2.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val networkChecker: NetworkChecker,
    private val dao: DAO,
    private val service: CourseRemoteService
) : CoursesRepository {

    override suspend fun updateCourse(course: Course) {
        withContext(Dispatchers.IO) {
            dao.updateBookmark(course.toEntity())
        }
    }

    override suspend fun getAllRemoteCourses(): List<RemoteCourse> {
        if(!networkChecker.hasInternetConnection()){
            throw IllegalArgumentException("mo internet connection")
        }
        val remoteCourses = service.loadCourses().courseList.map { it.toDomain() }
        return remoteCourses
    }

    override suspend fun getAllLocalCourses(): Flow<List<Course>> {
        val localCourses = withContext(Dispatchers.IO) {
            dao.getAllCourses()
        }
        val listDomain = localCourses.map { list-> list.map { it.toDomain() } }
        return listDomain
    }

    override suspend fun getAllFavoriteCourses(): Flow<List<Course>> =
        dao.getAllFavoritesCourses().map { list -> list.map { it.toDomain() } }

    override suspend fun getCoursesSortedByPublishDateDesc(): Flow<List<Course>> =
        dao.getCoursesSortedByPublishDateDesc().map { list -> list.map { it.toDomain() } }

    override suspend fun replaceLocalCourses(courses: List<Course>) {
        dao.clearCourses()
        dao.insertCourses(courses.map { it.toEntity() })
    }


    override suspend fun getCourseById(id: Int): Course{
        val courseEntity = dao.getCourseById(id)
        return courseEntity.toDomain()
    }
}