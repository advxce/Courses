package com.example.presentation.mapper

import androidx.core.content.ContextCompat

import com.example.domain2.CourseList

import com.example.domain2.LoadCoursesResult
import com.example.presentation.R
import com.example.presentation.data.CourseUI
import com.example.presentation.data.toUI
import javax.inject.Inject

class CourseUiMapper: LoadCoursesResult.Mapper<CourseState> {

    override fun mapSuccess(courses: CourseList): CourseState {
        val courseUiList: List<CourseUI> = courses.listCourses.mapIndexed { index, course-> course.toUI() }
        return CourseState.Success(courseUiList)
    }

    override fun mapError(message: String): CourseState = CourseState.Error("Error")


}

sealed class CourseState{
    object Loading : CourseState()
    data class Success( val courses:List<CourseUI>): CourseState()
    data class Error(val message:String): CourseState()

}