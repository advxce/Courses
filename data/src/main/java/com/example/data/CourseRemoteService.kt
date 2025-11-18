package com.example.data

import retrofit2.http.GET

interface CourseRemoteService {
    @GET
    suspend fun loadCourses(): CourseListRemoteData
}