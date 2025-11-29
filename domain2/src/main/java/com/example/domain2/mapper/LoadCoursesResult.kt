package com.example.domain2.mapper

import com.example.domain2.entity.CourseList

interface LoadCoursesResult {
    fun <T: Any> map(mapper:Mapper<T>):T

    interface Mapper<T:Any>{
        fun mapSuccess(courses: CourseList): T
        fun mapError(message:String):T
    }

    data class Success(private val courses: CourseList): LoadCoursesResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapSuccess(courses)
        }
    }

    data class Error(private val message: String): LoadCoursesResult{
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapError(message)
        }

    }
}