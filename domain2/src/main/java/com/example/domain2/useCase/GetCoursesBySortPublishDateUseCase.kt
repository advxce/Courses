package com.example.domain2.useCase

import com.example.domain2.entity.Course
import com.example.domain2.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetCoursesBySortPublishDateUseCase {
    suspend fun getCoursesSortedByPublishDateDesc(): Flow<List<Course>>
}

class GetCoursesBySortPublishDateUseCaseImpl @Inject constructor(
    private val repository: CoursesRepository
): GetCoursesBySortPublishDateUseCase {
    override suspend fun getCoursesSortedByPublishDateDesc(): Flow<List<Course>> {
        return repository.getCoursesSortedByPublishDateDesc()
    }
}