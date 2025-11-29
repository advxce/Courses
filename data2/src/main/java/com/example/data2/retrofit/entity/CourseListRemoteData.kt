package com.example.data2.retrofit.entity

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CourseListRemoteData(
    @SerializedName("courses")
    val courseList: List<CourseRemoteData>
)