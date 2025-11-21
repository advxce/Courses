package com.example.data2

import android.util.Log
import com.example.data2.database.CourseEntity
import com.example.data2.database.DAO
import com.example.data2.database.toDomain
import com.example.data2.database.toEntity
import com.example.domain2.Course
import com.example.domain2.CourseList
import com.example.domain2.CoursesRepository
import com.example.domain2.LoadCoursesResult
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

    override suspend fun getAllCourses(): LoadCoursesResult {
        return try {
            Log.i("Data", "internet: ${networkChecker.hasInternetConnection()}")

            if(networkChecker.hasInternetConnection()){
                val response = service.loadCourses()
                if (response.isSuccessful){
                    val remoteCourses = response.body()
                    remoteCourses?.let {
                        mergeRemoteAndLocalCourses(remoteCourses.courseList.map { it.toEntity() })
                    }
                } else {
                    Log.w("Data", "Request error, continue with local data")
                }
            }

            val localCourses = withContext(Dispatchers.IO) {
                dao.getAllCourses().first()
            }
            Log.i("Data", "Loaded ${localCourses.size} courses from DB")
            val domainCourses = localCourses.map { it.toDomain() }
            LoadCoursesResult.Success(CourseList(listCourses = domainCourses))

        } catch (e: Exception) {
            Log.e("Data", "Failed load courses: ${e.message}", e)
            LoadCoursesResult.Error("Failed load courses: ${e.message}")
        }
    }

    private suspend fun mergeRemoteAndLocalCourses(remoteCourses: List<CourseEntity>) {
        withContext(Dispatchers.IO) {
            val localCourses = dao.getAllCourses().first()

            val localBookmarksMap = localCourses.associateBy { it.id }

            val mergedCourses = remoteCourses.map { remoteCourse ->
                val localCourse = localBookmarksMap[remoteCourse.id]
                remoteCourse.copy(
                    isBookmarked = localCourse?.isBookmarked ?: false
                )
            }
            dao.insertCourses(mergedCourses)
            Log.i("Data", "Merged ${mergedCourses.size} courses, preserved bookmarks")
        }
    }

    override suspend fun updateCourse(course: Course) {
        withContext(Dispatchers.IO) {
            dao.updateBookmark(course.toEntity())
            Log.i("Data", "Updated bookmark for course ${course.id}: ${course.isBookmarked}")
        }
    }

    override suspend fun getAllFavoriteCourses(): Flow<List<Course>> =
        dao.getAllFavoritesCourses().map { list -> list.map { it.toDomain() } }




    override suspend fun getCourseById(id: Int): Course{
        val courseEntity = dao.getCourseById(id)
        return courseEntity.toDomain()
    }
}