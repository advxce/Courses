package com.example.data

import com.example.domain.CourseList
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CourseListRemoteData(
    @SerializedName("courses")
    val courseList: List<CourseRemoteData>
) {
    fun toDomain(): CourseList {
        return CourseList(listCourses = courseList.map { it.toDomain() })
    }
}
