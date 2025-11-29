package com.example.domain2.useCase

import com.example.domain2.entity.Course
import com.example.domain2.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetAllFavoritesCoursesUseCase{
    suspend fun invoke(): Flow<List<Course>>
}

class GetAllFavoritesCoursesUseCaseImpl @Inject constructor(
    private val repository: CoursesRepository
): GetAllFavoritesCoursesUseCase {
    override suspend fun invoke(): Flow<List<Course>> {
       val favoriteCourses = withContext(Dispatchers.IO){
           repository.getAllFavoriteCourses()
       }
       return favoriteCourses
    }
}