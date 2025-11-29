package com.example.domain2.useCase

import android.util.Log
import com.example.domain2.entity.CourseList
import com.example.domain2.entity.toCourse
import com.example.domain2.mapper.LoadCoursesResult
import com.example.domain2.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetAllCoursesUseCase{
    suspend fun invoke(): LoadCoursesResult
}

class GetAllCoursesUseCaseImpl @Inject constructor(
    private val repository: CoursesRepository
): GetAllCoursesUseCase {
    override suspend fun invoke(): LoadCoursesResult {
        return try {
            val localCourses = withContext(Dispatchers.IO) {
                repository.getAllLocalCourses().first()
            }

            val remoteCourses = try {
                repository.getAllRemoteCourses()
            }
            catch (e: Exception){
                return LoadCoursesResult.Success(CourseList(listCourses = localCourses))
            }

           if(remoteCourses != localCourses){
              repository.replaceLocalCourses(remoteCourses.map { it.toCourse() })
           }
            LoadCoursesResult.Success(CourseList(listCourses = localCourses))


        }
        catch (e:Exception){
            LoadCoursesResult.Error("Cant get access to network")
        }
    }


}