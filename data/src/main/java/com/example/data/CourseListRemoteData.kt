package com.example.data

import com.example.domain.CourseList

data class CourseListRemoteData(
    val courseList: List<CourseRemoteData>
) {
    fun toDomain(): CourseList {
        return CourseList(listCourses = courseList.map { it.toDomain() })
    }
}
