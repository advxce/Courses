package com.example.data2

import android.util.Log
import com.example.data2.database.DAO
import com.example.data2.database.toDomain
import com.example.data2.database.toEntity
import com.example.data2.retrofit.CourseListRemoteData
import com.example.domain.CourseList
import com.example.domain.CoursesRepository
import com.example.domain.LoadCoursesResult
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val networkChecker: NetworkChecker,
    private val dao: DAO,
    private val service: CourseRemoteService
) : CoursesRepository {

    override suspend fun loadCourses(): LoadCoursesResult {
        return try {
            val localCourses = dao.getAllCourses()
            Log.i("CheckData", "Local:" +localCourses.toString())
            return if (localCourses.isNotEmpty()) {
                LoadCoursesResult.Success(
                    CourseList(localCourses.map { it.toDomain() })
                )
            } else {
                if (networkChecker.hasInternetConnection()) {
                    val response: Response<CourseListRemoteData> = service.loadCourses()
                    if (response.isSuccessful) {
                        response.body()?.let { body ->

//                            val domain = body.toDomain()

                            val mergedCourses = response.body()!!.courseList.map { remote ->
                                remote.toDomain()
                            }
                            Log.i("CheckData", "Merged"+ mergedCourses.toString())
                            dao.insertCourses(mergedCourses.map { it.toEntity() })

                            LoadCoursesResult.Success(CourseList(mergedCourses))
                        } ?: LoadCoursesResult.Error("Empty server response")
                    }else {
                        LoadCoursesResult.Error("Server error: ${response.code()}")
                    }
                } else {
                    LoadCoursesResult.Error("No internet connection and empty DB")
                }
            }
        } catch (e: Exception) {
            LoadCoursesResult.Error(e.message ?: "Error loading")
        }
    }

    override suspend fun getBookmarkedCourses(): LoadCoursesResult {
        val list = dao.getBookmarkedCourses()
        Log.i("CheckData", "BookMarkList "+ list.toString())
        return if (list.isNotEmpty()) {
            LoadCoursesResult.Success(CourseList(list.map { it.toDomain() }))
        } else {
            LoadCoursesResult.Error("no Favourite Courses")
        }
    }

    override suspend fun toggleBookmark(id: Int, currentValue: Boolean) {

        val newValue = !currentValue
        dao.updateBookmark(id, newValue)

        Log.i("CheckData", "BookmarkValue $newValue")
    }

}