package com.example.data

import com.example.domain.CoursesRepository
import com.example.domain.LoadCoursesResult
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(

    private val service: CourseRemoteService
) : CoursesRepository {



    override suspend fun loadCourses(): LoadCoursesResult {
        return try {
            val response: Response<CourseListRemoteData> = service.loadCourses()
            if(response.isSuccessful){
                val coursesResponse = response.body()
                if(coursesResponse != null){


                    val courses = coursesResponse.toDomain()
                    LoadCoursesResult.Success(courses)
                } else {
                    LoadCoursesResult.Error("Empty response")
                }
            }
            else{
                LoadCoursesResult.Error("Server Error")
            }


        } catch (e: Exception) {
            LoadCoursesResult.Error(e.message ?: "error")
        }
    }
}