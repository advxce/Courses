package com.example.data

import retrofit2.Response
import retrofit2.http.GET


interface CourseRemoteService {
    @GET("u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun loadCourses(): Response<CourseListRemoteData>
}