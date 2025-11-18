package com.example.domain


interface LoadCoursesResult {
    fun <T: Any> map(mapper:Mapper<T>):T

    interface Mapper<T:Any>{
        fun mapSuccess(courses: Courses):T
        fun mapError(message:String):T
    }

    data class Success(private val courses: Courses): LoadCoursesResult {
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